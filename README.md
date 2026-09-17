# Bashar Shop — Kotlin + Firebase

This is a starter e-commerce Android project with:
- Home/product list
- Search
- Product details
- Cart
- Checkout / Cash on Delivery
- Firebase Firestore orders/products
- Firebase Auth admin login
- Admin role check
- Admin product creation

## Firebase setup
1. Create a Firebase project.
2. Add Android app with package: `com.bashar.shop`
3. Download `google-services.json`.
4. Put it at: `app/google-services.json`
5. Enable Authentication > Email/Password.
6. Enable Firestore Database and Storage.

## Admin setup
Create an admin email/password in Firebase Authentication.
Then create Firestore document:
`admins/{ADMIN_UID}`
with:
`admin: true`

Do NOT hard-code an admin password in the APK. The app uses Firebase Authentication.

## Firestore collections
`products`
- name: string
- price: number
- category: string
- imageUrl: string
- description: string

`orders`
- orderId
- name
- phone
- address
- total
- status
- items
- userId
- createdAt

## Important
Before publishing, add Firebase Security Rules. Do not leave Firestore/Storage open to everyone.
