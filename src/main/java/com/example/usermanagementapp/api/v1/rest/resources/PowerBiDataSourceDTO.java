package com.example.usermanagementapp.api.v1.rest.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PowerBiDataSourceDTO {
    private List<Map<String, Object>> stores;
    private List<Map<String, Object>> traffic;
    private Map<String, String> directions;
    private Map<String, String> viewables;
    private Map<String, String> countTargetTypes;

    public PowerBiDataSourceDTO() {
    }

    public PowerBiDataSourceDTO(List<Map<String, Object>> stores, List<Map<String, Object>> traffic, Map<String, String> directions, Map<String, String> viewables, Map<String, String> countTargetTypes) {
        this.stores = stores;
        this.traffic = traffic;
        this.directions = directions;
        this.viewables = viewables;
        this.countTargetTypes = countTargetTypes;
    }

    public List<Map<String, Object>> getStores() {
        return stores;
    }

    public void setStores(List<Map<String, Object>> stores) {
        this.stores = stores;
    }

    public List<Map<String, Object>> getTraffic() {
        return traffic;
    }

    public void setTraffic(List<Map<String, Object>> traffic) {
        this.traffic = traffic;
    }

    public Map<String, String> getDirections() {
        return directions;
    }

    public void setDirections(Map<String, String> directions) {
        this.directions = directions;
    }

    public Map<String, String> getViewables() {
        return viewables;
    }

    public void setViewables(Map<String, String> viewables) {
        this.viewables = viewables;
    }

    public Map<String, String> getCountTargetTypes() {
        return countTargetTypes;
    }

    public void setCountTargetTypes(Map<String, String> countTargetTypes) {
        this.countTargetTypes = countTargetTypes;
    }
}