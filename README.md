### REST Implementation

[REST route](src/main/kotlin/com/example/routes/rest.kt)

- Authentication is not needed
- Endpoint L19 (_clientcode_ is found in adobe target, sessionId can be handled manually)
- The payload L20 represent a minimal payload to be sent, there's more params available ([Delivery API](https://developers.adobetarget.com/api/delivery-api/))
- The name of the Mbox (Target route) is defined on L37 and named _local_

__Pro__
- No library

### SDK implementation

- The library can be found at _com.adobe.target:target-java-sdk:2.3.0_
- The _cliencode_ L12 can be found in Target Admin
- The _organzitationId_ L13 can be found in adobe Target
- The channel can be defined in the request builder L24 (it's usually _web_ or _mobile_)

__Pro__
- Intellisense
- Less code
