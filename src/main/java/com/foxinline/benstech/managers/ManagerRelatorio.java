/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.foxinline.benstech.managers;

import com.foxinline.benstech.models.Bem;
import com.foxinline.benstech.models.Manutencao;
import com.foxinline.benstech.models.TipoProduto;
import com.foxinline.benstech.services.ServiceBem;
import com.foxinline.benstech.services.ServiceManutencao;
import com.foxinline.benstech.services.ServiceTipoProduto;
import com.foxinline.benstech.utilities.Formatador;
import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author marcio
 */
@Named
@ViewScoped
public class ManagerRelatorio implements Serializable {

    @EJB
    private ServiceBem serviceBem;
    @EJB
    private ServiceTipoProduto serviceTipoProduto;

    @EJB
    private ServiceManutencao serviceManutencao;

    private List<Bem> bensManutencao;
    private List<Bem> bensManutencaoFiltro;
    private List<TipoProduto> tipos;
    private List<Bem> invatario;
    private boolean manutencaoSwitch;
    private List<Bem> bensTotal;

    private Formatador formatador;

    private String idTipoSelecionado;
    private TipoProduto tipoSelecionado;
    private String tipoSelecionadoNome;

    @PostConstruct
    public void init() {
        this.bensManutencao = new ArrayList<>();
        this.bensManutencaoFiltro = new ArrayList<>();
        this.invatario = new ArrayList<>();
        this.formatador = new Formatador();

        this.tipos = new ArrayList<>();
        this.bensTotal = new ArrayList<>();
        tipos = serviceTipoProduto.findAll();
        this.bensTotal = serviceBem.findAll();
        bensManutencaoFiltro.addAll(bensTotal);
        filtrarPorTipo();
    }

    public void filtrarPorTipo() {

        bensManutencaoFiltro.clear();
        if ((idTipoSelecionado == "" || idTipoSelecionado == null) && manutencaoSwitch == false) {
            bensManutencaoFiltro.addAll(bensTotal);
            tipoSelecionadoNome = "Todos";
            System.out.println("caso tipo false botao false");

            return;
        }

        if (manutencaoSwitch == false) {
            tipoSelecionado = serviceTipoProduto.findById(Long.valueOf(idTipoSelecionado));
            tipoSelecionadoNome = tipoSelecionado.getTipo();
            for (Bem bem : bensTotal) {
                if (bem.getTipoProduto().equals(tipoSelecionado)) {
                    bensManutencaoFiltro.add(bem);
                }
            }
            System.out.println("caso tipo selecionado botao false");
            System.out.println(bensManutencaoFiltro);
            return;
        }

        if ((idTipoSelecionado == "" || idTipoSelecionado == null) && manutencaoSwitch == true) {
            System.out.println("caso tipo false botao true");

            for (Bem bem : bensTotal) {
                if (manutencaoPreventiva(bem)) {
                    bensManutencaoFiltro.add(bem);
                }
            }
            return;
        }

        System.out.println("caso tipo true botao true");
        tipoSelecionado = serviceTipoProduto.findById(Long.valueOf(idTipoSelecionado));
        tipoSelecionadoNome = tipoSelecionado.getTipo();

        for (Bem bem : bensTotal) {
            if (manutencaoPreventiva(bem) && bem.getTipoProduto().equals(tipoSelecionado)) {
                bensManutencaoFiltro.add(bem);
            }
        }
    }

    public boolean manutencaoPreventiva(Bem bemSelecionado) {

        List<Manutencao> manutencoes = new ArrayList<>();
        manutencoes = serviceManutencao.buscarManutencaoBemId(bemSelecionado.getId());

        int mesesBem = 0;
        mesesBem += (LocalDate.now().getYear() - bemSelecionado.getDataCompra().getYear()) * 12;
        mesesBem += (LocalDate.now().getMonthValue() - bemSelecionado.getDataCompra().getMonthValue());
        if (manutencoes.isEmpty() && mesesBem >= 12) {
            return true;
        }
        for (Manutencao m : manutencoes) {
            int meses = 0;
            meses += (LocalDate.now().getYear() - m.getDataManutencao().getYear()) * 12;
            meses += (LocalDate.now().getMonthValue() - m.getDataManutencao().getMonthValue());
            System.out.println(meses);
            if (meses <= 12) {
                return false;
            } else {
                return true;
            }
        }
        return false;

    }

    public void gerarPDFManutencao() throws FileNotFoundException, DocumentException, IOException {

        Document document = new Document(PageSize.A4);
        document.setMargins(40f, 40f, 55f, 40f);

        try {

            PdfWriter.getInstance(document, new FileOutputStream("relatorioManutencao.pdf"));

            document.open();
            Image logo = Image.getInstance("logoColorida.png");
            logo.scaleAbsolute(120, 54);
            logo.setAlignment(Element.ALIGN_LEFT);
            logo.setAbsolutePosition(20, 771);
            document.add(logo);

            Paragraph titulo;
            if (manutencaoSwitch) {
                titulo = new Paragraph("Relatório de manutenção", FontFactory.getFont(FontFactory.COURIER_BOLD, 15));
            } else {
                titulo = new Paragraph("Relatório de Inventário", FontFactory.getFont(FontFactory.COURIER_BOLD, 15));
            }
            titulo.setAlignment(Element.ALIGN_CENTER);

            Paragraph subTitulo = new Paragraph("Relatório emitido dia " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " às "
                    + LocalTime.now(ZoneId.of("America/Sao_Paulo")).format(DateTimeFormatter.ofPattern("HH:mm:ss"))
                    + "\nTipo selecionado: " + this.tipoSelecionadoNome,
                    FontFactory.getFont(FontFactory.COURIER_BOLDOBLIQUE, 8));

            document.add(titulo);
            document.add(subTitulo);

            Table tabelaBensParaManutencao = new Table(6);

            tabelaBensParaManutencao.setBorder(1);
            tabelaBensParaManutencao.setBorderWidth(3);
            tabelaBensParaManutencao.setWidth(100);
            tabelaBensParaManutencao.setWidths(new float[]{25, 15, 15, 15, 15, 15});
            tabelaBensParaManutencao.setPadding(2.1f);

            Paragraph paragrafoNomeBem = new Paragraph(new Phrase(20, "Nome",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12f)));
            paragrafoNomeBem.setAlignment(Element.ALIGN_CENTER);

            Paragraph paragrafoTipoProduto = new Paragraph(new Phrase(20, "Tipo",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12f)));
            paragrafoTipoProduto.setAlignment(Element.ALIGN_CENTER);

            Paragraph paragrafoDataCompra = new Paragraph(new Phrase(20, "Data da compra",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12f)));
            paragrafoDataCompra.setAlignment(Element.ALIGN_CENTER);

            Paragraph paragrafoPreco = new Paragraph(new Phrase(20, "Preço da compra",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12f)));
            paragrafoPreco.setAlignment(Element.ALIGN_CENTER);

            Paragraph paragrafoResidual = new Paragraph(new Phrase(20, "Residual",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12f)));
            paragrafoResidual.setAlignment(Element.ALIGN_CENTER);

            Paragraph paragrafoVidaUtil = new Paragraph(new Phrase(20, "Vida Útil",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12f)));
            paragrafoVidaUtil.setAlignment(Element.ALIGN_CENTER);

            Cell celulaNome = new Cell(paragrafoNomeBem);
            Cell celulaTipo = new Cell(paragrafoTipoProduto);
            Cell celulaDataCompra = new Cell(paragrafoDataCompra);
            Cell celulaPreco = new Cell(paragrafoPreco);
            Cell celulaResidual = new Cell(paragrafoResidual);
            Cell celulaVidaUtil = new Cell(paragrafoVidaUtil);

            tabelaBensParaManutencao.addCell(celulaNome);
            tabelaBensParaManutencao.addCell(celulaTipo);
            tabelaBensParaManutencao.addCell(celulaDataCompra);
            tabelaBensParaManutencao.addCell(celulaPreco);
            tabelaBensParaManutencao.addCell(celulaResidual);
            tabelaBensParaManutencao.addCell(celulaVidaUtil);

            for (Bem bem : bensManutencaoFiltro) {

                Paragraph paragrafoNomeBemValor = new Paragraph(new Phrase(20, bem.getNomeProduto(),
                        FontFactory.getFont(FontFactory.HELVETICA, 12f)));
                paragrafoNomeBemValor.setAlignment(Element.ALIGN_CENTER);

                Paragraph paragrafoTipoProdutoValor = new Paragraph(new Phrase(20, bem.getTipoProduto().getTipo(),
                        FontFactory.getFont(FontFactory.HELVETICA, 12f)));
                paragrafoTipoProdutoValor.setAlignment(Element.ALIGN_CENTER);

                Paragraph paragrafoDataCompraValor = new Paragraph(new Phrase(20, bem.getDataCompra().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        FontFactory.getFont(FontFactory.HELVETICA, 12f)));
                paragrafoDataCompraValor.setAlignment(Element.ALIGN_CENTER);

                Paragraph paragrafoPrecoValor = new Paragraph(new Phrase(20, formatador.getFormatadorMoeda().format(bem.getPrecoCompra()),
                        FontFactory.getFont(FontFactory.HELVETICA, 12f)));
                paragrafoPrecoValor.setAlignment(Element.ALIGN_CENTER);

                Paragraph paragrafoResidualValor = new Paragraph(new Phrase(20, formatador.getFormatadorMoeda().format(bem.getValorResidual()),
                        FontFactory.getFont(FontFactory.HELVETICA, 12f)));
                paragrafoResidualValor.setAlignment(Element.ALIGN_CENTER);

                Paragraph paragrafoVidaUtilValor = new Paragraph(new Phrase(20, String.valueOf(bem.getVidaUtil()),
                        FontFactory.getFont(FontFactory.HELVETICA, 12f)));
                paragrafoVidaUtilValor.setAlignment(Element.ALIGN_CENTER);

                Cell celulaNomeValor = new Cell(paragrafoNomeBemValor);
                Cell celulaTipoValor = new Cell(paragrafoTipoProdutoValor);
                Cell celulaDataCompraValor = new Cell(paragrafoDataCompraValor);
                Cell celulaPrecoValor = new Cell(paragrafoPrecoValor);
                Cell celulaResidualValor = new Cell(paragrafoResidualValor);
                Cell celulaVidaUtilValor = new Cell(paragrafoVidaUtilValor);

                tabelaBensParaManutencao.addCell(celulaNomeValor);
                tabelaBensParaManutencao.addCell(celulaTipoValor);
                tabelaBensParaManutencao.addCell(celulaDataCompraValor);
                tabelaBensParaManutencao.addCell(celulaPrecoValor);
                tabelaBensParaManutencao.addCell(celulaResidualValor);
                tabelaBensParaManutencao.addCell(celulaVidaUtilValor);

            }

            document.add(tabelaBensParaManutencao);

            String os = System.getProperty("os.name").toLowerCase();
            Runtime rt = Runtime.getRuntime();
            try {
                if (os.contains("win")) {
                    // Windows
                    rt.exec("rundll32 url.dll,FileProtocolHandler " + "relatorioManutencao.pdf");
                } else if (os.contains("mac")) {
                    // Mac
                    rt.exec("open " + "/relatorioManutencao.pdf");
                } else if (os.contains("nix") || os.contains("nux")) {
                    // Linux/Unix
                    // Tenta abrir no navegador padrão
                    String[] browsers = {"xdg-open", "google-chrome", "firefox", "opera",
                        "epiphany", "konqueror", "mozilla", "netscape"};

                    String browser = null;
                    for (String b : browsers) {
                        if (Runtime.getRuntime().exec(new String[]{"which", b}).waitFor() == 0) {
                            browser = b;
                            break;
                        }
                    }
                    if (browser == null) {
                        throw new Exception("Nenhum navegador encontrado");
                    } else {
                        rt.exec(new String[]{browser, "relatorioManutencao.pdf"});
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                // Tratar o erro adequadamente
            }
            document.close();

        } catch (DocumentException | IOException e) {
            System.out.println(e);
        }

    }

    public ServiceBem getServiceBem() {
        return serviceBem;
    }

    public void setServiceBem(ServiceBem serviceBem) {
        this.serviceBem = serviceBem;
    }

    public List<Bem> getBensManutencao() {
        return bensManutencao;
    }

    public void setBensManutencao(List<Bem> bensManutencao) {
        this.bensManutencao = bensManutencao;
    }

    public String getIdTipoSelecionado() {
        return idTipoSelecionado;
    }

    public void setIdTipoSelecionado(String idTipoSelecionado) {
        this.idTipoSelecionado = idTipoSelecionado;
    }

    public List<Bem> getBensManutencaoFiltro() {
        return bensManutencaoFiltro;
    }

    public void setBensManutencaoFiltro(List<Bem> bensManutencaoFiltro) {
        this.bensManutencaoFiltro = bensManutencaoFiltro;
    }

    public ServiceTipoProduto getServiceTipoProduto() {
        return serviceTipoProduto;
    }

    public void setServiceTipoProduto(ServiceTipoProduto serviceTipoProduto) {
        this.serviceTipoProduto = serviceTipoProduto;
    }

    public List<TipoProduto> getTipos() {
        return tipos;
    }

    public void setTipos(List<TipoProduto> tipos) {
        this.tipos = tipos;
    }

    public ServiceManutencao getServiceManutencao() {
        return serviceManutencao;
    }

    public void setServiceManutencao(ServiceManutencao serviceManutencao) {
        this.serviceManutencao = serviceManutencao;
    }

    public TipoProduto getTipoSelecionado() {
        return tipoSelecionado;
    }

    public void setTipoSelecionado(TipoProduto tipoSelecionado) {
        this.tipoSelecionado = tipoSelecionado;
    }

    public String getTipoSelecionadoNome() {
        return tipoSelecionadoNome;
    }

    public void setTipoSelecionadoNome(String tipoSelecionadoNome) {
        this.tipoSelecionadoNome = tipoSelecionadoNome;
    }

    public List<Bem> getInvatario() {
        return invatario;
    }

    public void setInvatario(List<Bem> invatario) {
        this.invatario = invatario;
    }

    public boolean isManutencaoSwitch() {
        return manutencaoSwitch;
    }

    public void setManutencaoSwitch(boolean manutencaoSwitch) {
        this.manutencaoSwitch = manutencaoSwitch;
    }

    public Formatador getFormatador() {
        return formatador;
    }

    public void setFormatador(Formatador formatador) {
        this.formatador = formatador;
    }

}
