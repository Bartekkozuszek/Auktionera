# API IDEAS

| METHOD | PATH                      | QUERY/PARAM/BODY                            | DESC                         | RESPONSE  |
| ------ | ------------------------- | ------------------------------------------- | ---------------------------- | --------- |
| GET    | /api/signout              |                                             |                              |           |
| GET    ok| /api/account              |                                             | get logged in account        | Account   |
| PATCH  ok| /api/account              | BODY: Obj with props<br>userName<br>address | edit logged in account       | Account   |
| GET    in progress| /api/account/auctions     | QUERIES: <br> filter[] <br> sort[]          | history of auctions          | Auction[] |
| GET   in progress | /api/users                | QUERY: <br> filter[] <br> sort[]            | obfuscated result            | User[]    |
| GET   ok | /api/users/{id}           |                                             | obfuscated result            | User      |
| GET   in progress | /api/users/{id}/auctions  | QUERY: <br> filter[] <br> sort[]            | obfuscated result            | Auction[] |
| GET   in progress | /api/auctions             | QUERIES: <br> filter[] <br> sort[]          |                              | Auction[] |
| GET    ok| /api/auctions/{id}        |                                             |                              | Auction   |
| GET    | /api/auctions/{id}/images |                                             |                              | Image []  |
| DELETE ok| /api/auctions/{id}        |                                             | Seller can remove if no bids | Auction   |
| POST  ok| /api/auctions             | BODY: Auction                               |                              | Auction   |
| POST  ok| /api/auctions/{id}/bid    | BODY: Bid                                   |                              | Auction   |
| POST  ok| /api/auctions/{id}/review | BODY: Review                                | Add review.                  | Auction   |
| PUT    | /api/auctions/{id}/review | BODY: Review                                | Edit review.                 | Auction   |

```json

Account:
    {
        "user" : User,
        "email" : "string",
        "isAnonymousBuyer" : false,
        "address" : {
                        "streetName" : "string",
                        "postNr" : 12345,
                        "city" : "string"
                    }
    }

AccountRequest :
    {
        "userName" : "string",
        "streetName" : "string",
        "postNr" : 12345,
        "city" : "string",
        "isAnonymousBuyer" : false
    }

User:
    {
        "id" : 0,
        "userName" : "string",
        "createdAt" : Date,
    }

UserStats:
    {        
        "totalSales" : 0,
        "sellerRating" : 0,
        "buyerRating" : 0
    }

Review:
        {
            "auctionId" : 0,
            "seller" : User,
            "buyer" : User,
            "createdAt" : Date,
            "lastEditAt" : Date,
            "rating" : 0,
            "text" : "string"
        }

ReviewRequest:
    {
        "rating" : 0,
        "text" : "string"
    }

Auction:
        {
            "id" : 0,
            "tags" : ["string"],
            "description" : "string",
            "seller" : User,
            "buyer" : User,
            "sellerReview" : Review,
            "buyerReview" : Review,
            "state" : enum:AuctionState,
            "endsAt" : Date,
            "createdAt" : Date,
            "currentBidAt" : Date,
            "endedAt" : Date,
            "startPrice" : 0,
            "buyoutPrice" : 0,
            "minBidStep" : 0,
            "currentBid" : 0,
            "deliveryType" : enum:DeliveryType
        }
Bid:
     {
        "buyoutPrice" : 0,
        "minBidStep" : 0,
        "currentBid" : 0,
        "bid" : 0
      }

AuctionRequest:
    {
        "tags" : ["string"],
        "description" : "string",
        "endsAt" : Date,
        "startPrice" : 0,
        "buyoutPrice" : 0,
        "minBidStep" : 0,
        "deliveryType" : enum:DeliveryType,
        "images" : Image[]
    }

Image:  {
            "data" : byte [], eller base64 string,
            "description" : string
        }

enums:
    DeliveryType: [PickUpAtSellerHome, PickUpAtAddress, SellerSendsToAddress],
    AuctionState: [InProgress,EndedNotBought,EndedBought]

```
