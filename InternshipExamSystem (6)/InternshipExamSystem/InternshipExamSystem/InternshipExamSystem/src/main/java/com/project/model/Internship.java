package com.project.model;

public class Internship {

    private String companyName;
    private String role;
    private double stipend;
    private String deadline;
    private int internshipId;
    private double requiredCgpa;

    public double getRequiredCgpa() { return requiredCgpa; }
    public void setRequiredCgpa(double requiredCgpa) { this.requiredCgpa = requiredCgpa; }

    public int getInternshipId() { return internshipId; }
    public void setInternshipId(int internshipId) { this.internshipId = internshipId; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public double getStipend() { return stipend; }
    public void setStipend(double stipend) { this.stipend = stipend; }

    public String getDeadline() { return deadline; }
    public void setDeadline(String deadline) { this.deadline = deadline; }
}