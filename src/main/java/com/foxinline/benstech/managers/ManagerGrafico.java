package com.foxinline.benstech.managers;

import com.foxinline.benstech.models.Bem;
import com.foxinline.benstech.models.TipoProduto;
import com.foxinline.benstech.services.ServiceBem;
import com.foxinline.benstech.services.ServiceTipoProduto;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.primefaces.event.ItemSelectEvent;
import software.xdev.chartjs.model.charts.DoughnutChart;
import software.xdev.chartjs.model.charts.PieChart;
import software.xdev.chartjs.model.color.Color;
import software.xdev.chartjs.model.data.DoughnutData;
import software.xdev.chartjs.model.data.PieData;
import software.xdev.chartjs.model.dataset.DoughnutDataset;
import software.xdev.chartjs.model.dataset.PieDataset;
import software.xdev.chartjs.model.options.DoughnutOptions;

@Named
@RequestScoped
public class ManagerGrafico implements Serializable {

    private static final long serialVersionUID = 1L;
    private String json;
    @EJB
    private ServiceTipoProduto serviceTipoProduto;

    @EJB
    private ServiceBem serviceBem;

    private String pieModel;
    private List<Number> data;
    private List<String> nomesTipos;
    private List<TipoProduto> tipos;
    private List<Bem> bensPorTipo;
    private String donutModel;

    @PostConstruct
    public void init() {

        tipos = new ArrayList<>();
        tipos = serviceTipoProduto.findAll();
        nomesTipos = new ArrayList<>();
        for (TipoProduto t : tipos) {
            nomesTipos.add(t.getTipo());
        }

        bensPorTipo = new ArrayList<>();
        bensPorTipo = serviceBem.findAll();

        createDonutModel();
        createPieModel();
    }

//    public void atualizaGrafico() {
//        tipos = new ArrayList<>();
//
//        tipos = serviceTipoProduto.findAll();
//        nomesTipos = new ArrayList<>();
//        for (TipoProduto t : tipos) {
//            nomesTipos.add(t.getTipo());
//        }
//
//        bensPorTipo = new ArrayList<>();
//        bensPorTipo = serviceBem.findAll();
//    }

    private void createPieModel() {
        Map<String, Integer> mapTipos = new HashMap<>();
        Color[] cores = new Color[nomesTipos.size()];

        for (String s : nomesTipos) {
            mapTipos.put(s, 0);
            cores[nomesTipos.indexOf(s)] = (new Color(Color.random(), 1));
        }

        for (Bem bem : bensPorTipo) {
            if (nomesTipos.contains(bem.getTipoProduto().getTipo())) {
                mapTipos.replace(bem.getTipoProduto().getTipo(), mapTipos.get(bem.getTipoProduto().getTipo()) + 1);
            }
        }
        pieModel = new PieChart()
                .setData(new PieData()
                        .addDataset(new PieDataset()
                                .setDataUnchecked(mapTipos.values())
                                .setLabel("Itens")
                                .addBackgroundColors(cores)
                        )
                        .setLabels(nomesTipos))
                .toJson();
    }

    public void createDonutModel() {

        Map<String, Integer> mapTipos = new HashMap<>();
        Color[] cores = new Color[nomesTipos.size()];
        for (String s : nomesTipos) {
            mapTipos.put(s, 0);
            cores[nomesTipos.indexOf(s)] = (new Color(Color.random(), 1));
        }

        for (Bem bem : bensPorTipo) {
            if (nomesTipos.contains(bem.getTipoProduto().getTipo())) {
                mapTipos.replace(bem.getTipoProduto().getTipo(), mapTipos.get(bem.getTipoProduto().getTipo()) + 1);
            }
        }

        donutModel = new DoughnutChart()
                .setData(new DoughnutData()
                        .addDataset(new DoughnutDataset()
                                .setDataUnchecked(mapTipos.values())
                                .addBackgroundColors(cores)
                        )
                        .setLabels(nomesTipos))
                .setOptions(new DoughnutOptions().setMaintainAspectRatio(Boolean.FALSE))
                .toJson();
    }

    public void createJsonModel() {
        json = "{\r\n"
                + "   \"type\":\"line\",\r\n"
                + "   \"data\":{\r\n"
                + "      \"datasets\":[\r\n"
                + "         {\r\n"
                + "            \"backgroundColor\":\"rgba(40, 180, 99, 0.3)\",\r\n"
                + "            \"borderColor\":\"rgb(40, 180, 99)\",\r\n"
                + "            \"borderWidth\":1,\r\n"
                + "            \"data\":[\r\n"
                + "               {\r\n"
                + "                  \"x\":1699457269877,\r\n"
                + "                  \"y\":20\r\n"
                + "               },\r\n"
                + "               {\r\n"
                + "                  \"x\":1700047109694,\r\n"
                + "                  \"y\":20\r\n"
                + "               }\r\n"
                + "            ],\r\n"
                + "            \"hidden\":false,\r\n"
                + "            \"label\":\"Device Id: 524967 Register: A - total Wh \",\r\n"
                + "            \"minBarLength\":3\r\n"
                + "         },\r\n"
                + "         {\r\n"
                + "            \"backgroundColor\":\"rgba(218, 117, 255, 0.3)\",\r\n"
                + "            \"borderColor\":\"rgb(218, 117, 255)\",\r\n"
                + "            \"borderWidth\":1,\r\n"
                + "            \"data\":[\r\n"
                + "               {\r\n"
                + "                  \"x\":1699457267847,\r\n"
                + "                  \"y\":10\r\n"
                + "               },\r\n"
                + "               {\r\n"
                + "                  \"x\":1700047108397,\r\n"
                + "                  \"y\":234\r\n"
                + "               }\r\n"
                + "            ],\r\n"
                + "            \"hidden\":false,\r\n"
                + "            \"label\":\"Device Id: 524967 Register: A+ total Wh \",\r\n"
                + "            \"minBarLength\":3\r\n"
                + "         }\r\n"
                + "      ]\r\n"
                + "   },\r\n"
                + "   \"options\":{\r\n"
                + "      \"plugins\":{\r\n"
                + "         \"legend\":{\r\n"
                + "            \"display\":true,\r\n"
                + "            \"fullWidth\":true,\r\n"
                + "            \"position\":\"top\",\r\n"
                + "            \"reverse\":false,\r\n"
                + "            \"rtl\":false\r\n"
                + "         },\r\n"
                + "         \"title\":{\r\n"
                + "            \"display\":true,\r\n"
                + "            \"text\":\"Values from the meter\"\r\n"
                + "         },\r\n"
                + "         \"zoom\":{\r\n"
                + "            \"pan\":{\r\n"
                + "               \"enabled\":true,\r\n"
                + "               \"mode\":\"xy\",\r\n"
                + "               \"threshold\":5\r\n"
                + "            },\r\n"
                + "            \"zoom\":{\r\n"
                + "               \"wheel\":{\r\n"
                + "                  \"enabled\":true\r\n"
                + "               },\r\n"
                + "               \"pinch\":{\r\n"
                + "                  \"enabled\":true\r\n"
                + "               },\r\n"
                + "               \"mode\":\"xy\"\r\n"
                + "            }\r\n"
                + "         }\r\n"
                + "      },\r\n"
                + "      \"scales\":{\r\n"
                + "         \"x\":{\r\n"
                + "            \"beginAtZero\":false,\r\n"
                + "            \"offset\":true,\r\n"
                + "            \"reverse\":false,\r\n"
                + "            \"stacked\":true,\r\n"
                + "            \"ticks\":{\r\n"
                + "               \"autoSkip\":true,\r\n"
                + "               \"maxRotation\":0,\r\n"
                + "               \"minRotation\":0,\r\n"
                + "               \"mirror\":false,\r\n"
                + "               \"source\":\"data\"\r\n"
                + "            },\r\n"
                + "            \"time\":{\r\n"
                + "               \"displayFormats\":{\r\n"
                + "                  \"minute\":\"dd.LL T\"\r\n"
                + "               },\r\n"
                + "               \"round\":\"minute\",\r\n"
                + "               \"stepSize\":\"60\",\r\n"
                + "               \"unit\":\"minute\"\r\n"
                + "            },\r\n"
                + "            \"type\":\"time\"\r\n"
                + "         },\r\n"
                + "         \"y\":{\r\n"
                + "            \"beginAtZero\":false,\r\n"
                + "            \"offset\":false,\r\n"
                + "            \"reverse\":false,\r\n"
                + "            \"stacked\":true,\r\n"
                + "            \"ticks\":{\r\n"
                + "               \"autoSkip\":true,\r\n"
                + "               \"mirror\":false\r\n"
                + "            }\r\n"
                + "         }\r\n"
                + "      },\r\n"
                + "      \"showLine\":true,\r\n"
                + "      \"spanGaps\":false\r\n"
                + "   }\r\n"
                + "}";
    }

    public void itemSelect(ItemSelectEvent event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item selected",
                "Value: " + event.getData()
                + ", Item Index: " + event.getItemIndex()
                + ", DataSet Index:" + event.getDataSetIndex());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

//    public ServiceTipoProduto getServiceTipoProduto() {
//        return serviceTipoProduto;
//    }
//
//    public void setServiceTipoProduto(ServiceTipoProduto serviceTipoProduto) {
//        this.serviceTipoProduto = serviceTipoProduto;
//    }
//
//    public ServiceBem getServiceBem() {
//        return serviceBem;
//    }
//
//    public void setServiceBem(ServiceBem serviceBem) {
//        this.serviceBem = serviceBem;
//    }
    public String getPieModel() {
        return pieModel;
    }

    public void setPieModel(String pieModel) {
        this.pieModel = pieModel;
    }

    public List<Number> getData() {
        return data;
    }

    public void setData(List<Number> data) {
        this.data = data;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public ServiceTipoProduto getServiceTipoProduto() {
        return serviceTipoProduto;
    }

    public void setServiceTipoProduto(ServiceTipoProduto serviceTipoProduto) {
        this.serviceTipoProduto = serviceTipoProduto;
    }

    public ServiceBem getServiceBem() {
        return serviceBem;
    }

    public void setServiceBem(ServiceBem serviceBem) {
        this.serviceBem = serviceBem;
    }

    public List<String> getNomesTipos() {
        return nomesTipos;
    }

    public void setNomesTipos(List<String> nomesTipos) {
        this.nomesTipos = nomesTipos;
    }

    public List<TipoProduto> getTipos() {
        return tipos;
    }

    public void setTipos(List<TipoProduto> tipos) {
        this.tipos = tipos;
    }

    public List<Bem> getBensPorTipo() {
        return bensPorTipo;
    }

    public void setBensPorTipo(List<Bem> bensPorTipo) {
        this.bensPorTipo = bensPorTipo;
    }

    public String getDonutModel() {
        return donutModel;
    }

    public void setDonutModel(String donutModel) {
        this.donutModel = donutModel;
    }

}
