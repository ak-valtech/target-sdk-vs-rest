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

fun Route.sdk() {
    get("/sdk") {
        var mboxRequests = ArrayList<MboxRequest>()
        mboxRequests.add(MboxRequest().name("local").index(1))
        val exec = ExecuteRequest()
        exec.mboxes = mboxRequests
        val request = TargetDeliveryRequest.builder()
            .context(Context().channel(ChannelType.WEB))
            .execute(exec)
            .build()
        call.respondText(target.getOffers(request).toString())
    }
}
