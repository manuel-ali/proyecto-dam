package com.example._proyecto;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Importar {
    public List<Coche> importarXML(){
        List<Coche> coches = new ArrayList<>();
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse("src/main/resources/granturismo4_carlist.xml"); //Ubicación del xml a importar el cual está en el proyecto.

            //Cogemos los elementos que tengan la etiqueta Marca.
            NodeList nl = doc.getElementsByTagName("Marca");

            for (int i = 0; i < nl.getLength(); i++) {
                Node n = nl.item(i);
                if (n.getNodeType() == Node.ELEMENT_NODE) {
                    Element e = (Element) n;

                    String nombreMarca = e.getAttribute("nombre");
                    String paisMarca = e.getAttribute("pais");

                    if (!InsercionBd.marcaExiste(nombreMarca)){
                        InsercionBd.insertarMarca(nombreMarca, paisMarca);
                    }
                    Marca marca  = new Marca(nombreMarca, paisMarca); //Creamos un nuevo objeto marca a raíz de los atributos de la marca.

                    int idMarca = InsercionBd.obtenerIdMarca(nombreMarca);

                    if (idMarca != -1){
                        marca.setId(idMarca);
                    }

                    //Obtenemos los elementos con etiqueta coche.
                    NodeList c = e.getElementsByTagName("coche");
                    for (int j = 0; j < c.getLength(); j++) {
                        Element cocheElement = (Element) c.item(j);

                        String nombreCoche = cocheElement.getElementsByTagName("nombre").item(0).getTextContent();
                        int anyo = Integer.parseInt(cocheElement.getElementsByTagName("año").item(0).getTextContent());

                        if (!InsercionBd.cocheExiste(idMarca,nombreCoche,anyo)){
                            InsercionBd.insertarCoche(idMarca,nombreCoche,anyo);
                        }
                        Coche coche = new Coche(marca,nombreCoche,anyo); //Creamos un nuevo objeto coche con la marca y las etiquetas, nombre y año que contiene cada coche.

                        int id = InsercionBd.obtenerIdCoche(nombreCoche,anyo);

                        if (id != -1){
                            coche.setId(id);
                        }

                        coches.add(coche); //Añadimos el coche a la lista.
                    }
                }
            }
        }catch (ParserConfigurationException | IOException | SAXException e) {
            System.out.println(e.getMessage());
        }

        return coches;
    }

    public List<Coche> obtenerDatos(){
        return InsercionBd.obtenerDatos();
    }

    public List<Coche> importar() {
        List<Coche> coches;
        if (InsercionBd.contieneMarcas() && InsercionBd.contieneCoches()) {
            coches = obtenerDatos();
        } else {
            coches = importarXML();
        }
        return coches;
    }
}
