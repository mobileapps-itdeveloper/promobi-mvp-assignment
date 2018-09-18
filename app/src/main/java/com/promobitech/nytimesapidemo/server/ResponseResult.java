package com.promobitech.nytimesapidemo.server;

import com.promobitech.nytimesapidemo.room.tables.Movie;

import java.util.List;

public class ResponseResult {
    private List<Movie> results;
    private String status;
    private String num_results;
    private String has_more;
    private String copyright;

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNum_results() {
        return num_results;
    }

    public void setNum_results(String num_results) {
        this.num_results = num_results;
    }

    public String getHas_more() {
        return has_more;
    }

    public void setHas_more(String has_more) {
        this.has_more = has_more;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    @Override
    public String toString() {
        return "ClassPojo [results = " + results + ", status = " + status + ", num_results = " + num_results + ", has_more = " + has_more + ", copyright = " + copyright + "]";
    }
}
