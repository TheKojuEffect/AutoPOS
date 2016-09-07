class PurchaseLine {
    constructor() {
        this.id;
        this.item;
        this.buyer;
        this.remarks;
        this.quantity;
        this.rate;
    }

    get amount() {
        return this.quantity * this.rate;
    }
}