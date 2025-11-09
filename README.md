The following EatClub project is to demonstrate the working of APIs below:

**Swagger UI**  
`/eat-club/swagger-ui/index.html`

**Endpoints:**
1. **Active Deals**  
   `GET /eat-club/api/v1/deals/active?timeOfDay=9:00pm`

2. **Find Peak**  
   `GET /eat-club/api/v1/deals/peak`

Few notes:

MongoDB can be a good choice for storing schema-less provided data.

Reasons:
- Structure more suited towards document-oriented based database options like MongoDB.
- There are certain fields that variably appear — like start, end, open, close — as part of the deals object. MongoDB doesn't enforce a rigid schema, and we can store both as and when they appear. Due to this, we can add new fields in the future like birthDayDiscount, anniversaryDiscount, etc. as per the business needs.
- Embedding deals inside restaurants increases read performance for queries:
  Eg: Show all deals for Masala Kitchen, Show all restaurants where qtyLeft > 10
- Geospatial support in MongoDB.
- Horizontal scalability is possible with growing business.


Below er diagram made using LucidChart website:


<img width="247" height="233" alt="image" src="https://github.com/user-attachments/assets/f58cefa5-b251-4e2c-8781-1cee82b70355" />

