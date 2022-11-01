export interface User {
    userId: number
    email: string
    username: string
    password: string
}

export interface Stock {
    stockId: number,
    // purchaseDate: Date,
    purchaseDate: string
    symbol: string,
    companyName: string,
    quantity: number,
    stockPrice: number,
    totalPrice: number,
    stockValue: number,
    userId: number
}

export interface Email {
    recipient: string,
    msgBody: string,
    subject: string
}

export interface Response {
    message: string,
    code: number
}