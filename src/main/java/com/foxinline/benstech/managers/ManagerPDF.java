/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.foxinline.benstech.managers;

import com.foxinline.benstech.services.ServiceBem;
import com.foxinline.benstech.services.ServiceTipoProduto;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;

/**
 *
 * @author marcio
 */
@Named
@SessionScoped
public class ManagerPDF implements Serializable {

    @EJB
    private ServiceBem serviceBem;
    @EJB
    private ServiceTipoProduto serviceTipoProduto;

    @PostConstruct
    public void init() {

    }

    public void gerarPDFManutencao() throws FileNotFoundException, DocumentException, IOException {

        Document document = new Document(PageSize.A4);
        document.setMargins(40f, 40f, 40f, 40f);

        try {

            PdfWriter.getInstance(document, new FileOutputStream("/home/marcio/relatorioManutencao.pdf"));

            document.open();
            document.add(new Paragraph("Relatório para manutenção de ativos"));

            //Runtime.getRuntime().exec(new String[]{"/home/marcio/relatorioManutencao.pdf"});

            File myFile = new File("/home/marcio/relatorioManutencao.pdf");
            Desktop.getDesktop().open(myFile);
            
            document.close();

        } catch (DocumentException | IOException e) {
            System.out.println(e);
        }

    }

}
