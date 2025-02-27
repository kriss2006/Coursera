package org.example.resources;

import jakarta.annotation.security.PermitAll;
import org.example.models.StudentReport;
import org.example.services.ReportService;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Path("/reports")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReportResource {
    private final ReportService reportService;

    public ReportResource(ReportService reportService) {
        this.reportService = reportService;
    }

    @PermitAll
    @GET
    public Response getStudentReports(@QueryParam("pins") String pins,
                                      @QueryParam("minCredit") int minCredit,
                                      @QueryParam("startDate") String startDateStr,
                                      @QueryParam("endDate") String endDateStr) {

        Optional<List<StudentReport>> reports = reportService.getStudentReports(pins, minCredit, startDateStr, endDateStr);
        return Response.ok(reports.orElse(Collections.emptyList())).build();
    }
}