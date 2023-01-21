package com.example.lodge.viewmodel;

public class StateDataList<T> {
    //Enumerado que contiene los diferentes estados de la vista
    public enum DataStatus {
        CREATED,
        LOADING,
        SUCCESS,
        COMPLETE,
        NODATA, ORDERBYID,
    }

    private DataStatus status;
    //Los datos son de cualquier tipo <T>
    private T data;

    //Se crea el constructor con los valores iniciales que debe tener un objeto
    public StateDataList() {
        this.status = DataStatus.CREATED;
        this.data = null;
    }

    public StateDataList<T> loading() {
        this.status = DataStatus.LOADING;
        this.data = null;
        return this;
    }

    public StateDataList<T> nodata() {
        this.status = DataStatus.NODATA;
        this.data = null;
        return this;
    }

    public StateDataList<T> success(T data) {
        this.status = DataStatus.SUCCESS;
        this.data = data;
        return this;
    }

    public StateDataList<T> complete() {
        this.status = DataStatus.COMPLETE;

        return this;
    }

    public StateDataList<T> orderById(T data) {
        this.status = DataStatus.ORDERBYID;
        this.data = data;
        return this;

    }

    public DataStatus getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }
}
