package org.example.resources;

import io.dropwizard.auth.Auth;
import org.example.auth.UserToken;
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

    @GET
    public Response getStudentReports(@Auth UserToken userToken,
                                      @QueryParam("pins") String pins,
                                      @QueryParam("minCredit") int minCredit,
                                      @QueryParam("startDate") String startDateStr,
                                      @QueryParam("endDate") String endDateStr,
                                      @QueryParam("studentName") String studentName,
                                      @QueryParam("totalCredit") Integer totalCredit) {

        System.out.println("Authenticated user: " + userToken.getUsername());

        Optional<List<StudentReport>> reports = reportService.getStudentReports(pins, minCredit, startDateStr, endDateStr, studentName, totalCredit);
        return Response.ok(reports.orElse(Collections.emptyList())).build();
    }
}