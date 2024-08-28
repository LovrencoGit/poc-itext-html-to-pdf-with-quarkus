package org.acme.controllers;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.services.AttachmentService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Path("/attachments")
public class AttachmentResource {

    @Inject
    private AttachmentService attachmentService;


    //http://localhost:8080/attachments/DettaglioRichiamoMerci/ITEXT
    @GET
    @Path("/DettaglioRichiamoMerci/ITEXT")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getDettaglioRichiamoMerciITEXT() {
        ByteArrayOutputStream document = attachmentService.generateDettaglioRichiamoMerciITEXT();

        return Response.ok( document.toByteArray() )
                .header("Content-Disposition", "attachment; filename=\"DettaglioRichiamoMerci_output.pdf\"")
                .build();
    }

}
