import DenTravakAbstractElement from '../travak-abstract-element.js';

class DenTravakSandwichesCheckout extends DenTravakAbstractElement {

    connectedCallback() {
        super.connectedCallback();
        this.initEventListeners();
    }

    init(sandwich) {
        this.sandwich = sandwich;
        this.byId('sandwiches').innerHTML = ``;
        this.byId('sandwiches').appendChild(htmlToElement(this.getSandwichTemplate(this.sandwich)));
    }

    initEventListeners() {
        this.byId('order-button').addEventListener('click', e => this.orderSandwich());
        this.byId('back-button').addEventListener('click', e => this.app().showSandwichList());
    }

    orderSandwich() {
        //todo: call backend via fetch api
        let order = {};
        //order.phoneNumber = '012345677';
        let phone = this.byId('mobile-phone-number').value;
        let name = this.sandwich.name;
        let breadType = this.byId('radioBoterhammekes').value;
        let type = this.querySelectorAll('input[type=radio]');
        console.log(type);
        let labels = this.querySelectorAll('label[for=typeBrood]');
        console.log(labels);
        let boterham = this.byId("radioBoterhammekes").checked;
        let wrap = this.byId("radioWrap").checked;
        let turksbrood = this.byId("radioTurksBrood").checked;
        order.name = name;
        order.mobilePhoneNumber =phone;
        if(boterham){
            order.breadType = "boterhammekes";
        }
        else if(wrap){
            order.breadType = "wrap";
        }
        else if(turksbrood){
            order.breadType = "turksBrood";
        }
        console.log(this.sandwich);
        console.log(this.sandwich.id);
        console.log(this.sandwich.name);

        order.sandwichId = this.sandwich.id;

        console.log(order);

        fetch('http://localhost:8080/orders', {
            method: "POST", // *GET, POST, PUT, DELETE, etc.
            mode: "cors", // no-cors, cors, *same-origin
            cache: "no-cache", // *default, no-cache, reload, force-cache, only-if-cached
            headers: {
                "Content-Type": "application/json; charset=utf-8",
            },
            body: JSON.stringify(order),
        })
            .then(response => response.json())
            .then(res=>{console.log(res)})

        this.app().dispatchEvent(new CustomEvent('order-succeeded', {detail: order}));
    }

    get template() {
        return `
            <style>
                .form-group {
                    margin-bottom: 2rem;
                }
                .dt-header {
                    display: flex;
                }
                .dt-header button {
                    margin-left: auto;
                }
                div.dt-sandwich-info {
                    margin-left: auto;
                }
            </style>
            <div class="animate">
                <div class="dt-header">
                    <h3>Welkom bij den Travak</h3>
                    <button id="back-button" type="button" class="btn btn-primary">Terug</button>
                </div>
                <h4>Je geselecteerde broodje</h4>
                <div>
                <ul id="sandwiches" class="list-group"></ul>
                </div>
                <div class="form-group">
                    <label for="typeBrood"><h4>Kies het type brood</h4></label>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="typeBrood" id="radioBoterhammekes" value="option1">
                        <label class="form-check-label" for="radioBoterhammekes">
                            Boterhammekes
                        </label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="typeBrood" id="radioWrap" value="option2">
                        <label class="form-check-label" for="radioWrap">
                            Wrap
                        </label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="typeBrood" id="radioTurksBrood" value="option3">
                        <label class="form-check-label" for="radioTurksBrood">
                            Turks brood
                        </label>
                    </div>
                </div>
                <div class="form-group">
                    <label for="mobile-phone-number"><h4>Je GSM Nummer</h4></label>
                    <input type="text" class="form-control" id="mobile-phone-number" placeholder="0487/12 34 56">
                </div>

                <button id="order-button" class="btn btn-primary active">Bestellen</button>
            </div>
        `;
    }

    getSandwichTemplate(sandwich) {
        return `
            <a class="list-group-item">
                <button type="button" class="btn btn-primary bmd-btn-fab">
                    ${sandwich.name.charAt(0)}
                </button>
                <div class="bmd-list-group-col">
                    <p class="list-group-item-heading">${sandwich.name}</p>
                    <p class="list-group-item-text">${sandwich.ingredients}</p>
                </div>
                <div class="dt-sandwich-info">
                    <p class="list-group-item-text">${sandwich.price}</p>
                </div>
            </a>
        `;
    }
}

customElements.define('travak-sandwiches-checkout', DenTravakSandwichesCheckout);