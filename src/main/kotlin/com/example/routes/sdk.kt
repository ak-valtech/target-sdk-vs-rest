package com.example.routes

import com.adobe.target.edge.client.model.*
import com.adobe.target.delivery.v1.model.*
import com.adobe.target.edge.client.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


val clientConfig = ClientConfig.builder()
    .client("valtechsaemeapartner")
    .organizationId("E71EADC8584130D00A495EBD@AdobeOrg")
    .build()
val target = TargetClient.create(clientConfig)

const val targetUserIdSDK = "00000000000000000000000000000000000" // retrieved from the first response where it was not set in the payload (tntId)
const val targetingIdSDK = "123456" // external ID to associate with the user

fun Route.sdk() {
    get("/sdk") {
        var mboxRequests = ArrayList<MboxRequest>()
        val req = MboxRequest().name("local").index(1)
        req.parameters["testParam"] = "testParamValue"
        mboxRequests.add(req)
        val exec = ExecuteRequest()
        exec.mboxes = mboxRequests
        val request = TargetDeliveryRequest.builder()
            .context(Context().channel(ChannelType.WEB))
            .id(VisitorId()
                .tntId(targetUserIdSDK)
                .thirdPartyId(targetingIdSDK)
//                .marketingCloudVisitorId("THIS_IS_THE_MARKETING_CLOUD_ID")
                .customerIds(
                    arrayListOf(
                        CustomerId().id("THIS_ID_IS_THE_CUSTOMER_ID").integrationCode("Adobe Target").authenticatedState(AuthenticatedState.AUTHENTICATED),
                        CustomerId().id("ANOTHER_CUSTOMER_ID").integrationCode("Some other system").authenticatedState(AuthenticatedState.AUTHENTICATED)
                    )
                )
            )
            .execute(exec)
            .build()
        call.respondText(target.getOffers(request).toString())
    }
}
