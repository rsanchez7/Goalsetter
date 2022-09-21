package Goalsetter.pages;

import Goalsetter.config.TestBrowser;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//import org.openqa.selenium.interactions.Actions;
//import org.openqa.selenium.support.FindBy;
//import java.awt.event.KeyEvent;

public class PageGeneral extends TestBrowser {

    //@FindBy(xpath = "//button[contains(@class,'btn btn-primary btn-lg hidden-xs')]")
    //private WebElement WCREAR;
    //Actions ACTION = new Actions(driver);

    public static void sleep(int miliseconds) {
        try {
            Thread.sleep(miliseconds);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public void menu(String sModulo) {
        driver.findElement(By.xpath("//*[text()='" + sModulo + "']  //..")).click();
        sleep(2000);
    }

    public WebElement subMenu(String sSubModulo) {
        sSubModulo.toLowerCase();
        switch (sSubModulo) {
            case "usuarios":
                //Esperando corroboracion de nombre del key
                sSubModulo = "adm_users";
                break;
            case "video":
                //Esperando corroboracion de nombre del key
                sSubModulo = "adm_video";
                break;
            case "apis":
                //Esperando corroboracion de nombre del key
                sSubModulo = "adm_apis";
                break;
            case "buffering":
                //Esperando corroboracion de nombre del key
                sSubModulo = "adm_buffering";
                break;
            case "filtros":
                //Esperando corroboracion de nombre del key
                sSubModulo = "adm_content";
                break;
            default:
                System.out.println("El nombre del submódulo es incorrecto!");
        }

        WebElement wSubmenu = driver.findElement(By.xpath("//span[contains(@key,'" + sSubModulo + "')] //.. //.. //a"));
        return wSubmenu;
    }

    public void menu(String sModulo, String sSubModulo) {
        sModulo.toLowerCase();
        switch (sModulo) {
            case "sistema":
                //Esperando corroboracion de nombre del key
                sModulo = "adm_system";
                break;
            case "mails":
                //Esperando corroboracion de nombre del key
                sModulo = "adm_mails";
                break;
            case "estado de edges":
                //Esperando corroboracion de nombre del key
                sModulo = "adm_edges";
                break;
            case "pruebas service type":
                //Esperando corroboracion de nombre del key
                sModulo = "adm_service_type";
                break;
            case "dashboard":
                //Esperando corroboracion de nombre del key
                sModulo = "adm_dashboard";
                break;
            case "productos":
                //Esperando corroboracion de nombre del key
                sModulo = "adm_productos";
                break;
            case "contenidos":
                //Esperando corroboracion de nombre del key
                sModulo = "adm_filters";
                break;
            default:
                System.out.println("El nombre del módulo es incorrecto!");
        }

        //Selecciona el Menú
        driver.findElement(By.xpath("//span[contains(@key,'" + sModulo + "')] //.. //.. //a")).click();
        sleep(1000);
        //Selecciona el SubMenú
        subMenu(sSubModulo).click();
        //Para Usuarios sSubModulo = users
        //Para Listado Filtros sMobModulo = filtros
        sleep(2000);
    }

    public void clickHome() {
        driver.findElement(By.xpath("//h1[@class='logo-name clarovideo left'] //..")).click();
    }

    public void botonCrear() {
        //Click en cualquier botón Crear o Nuevo
        driver.findElement(By.xpath("//button[contains(@class,'btn btn-primary btn-lg hidden-xs')]")).click();
        sleep(1000);
    }

    public void botonCerrar() {
        //Click en cualquier botón Cerrar
        driver.findElement(By.xpath("//button[@class='close']")).click();
    }

    public void botonCancelar() {
        //Click en cualquier botón Crear
        driver.findElement(By.xpath("//button[contains(text(),'Cancelar')]")).click();
    }

    public void paginadorSiguiente() {
        driver.findElement(By.xpath("//ul[@class='pagination'] //li[contains(@id, 'next')] //a")).click();
    }

    public void paginadorAnterior() {
        driver.findElement(By.xpath("//ul[@class='pagination'] //li[contains(@id, 'previous')] //a")).click();
    }

    public void paginadorUltimo() {
        driver.findElement(By.xpath("//ul[@class='pagination'] //li[contains(@id, 'last')] //a")).click();
    }

    public void paginadorPrimero() {
        driver.findElement(By.xpath("//ul[@class='pagination'] //li[contains(@id, 'first')] //a")).click();
    }

    public void paginadorNumero(String sNumero) {
        driver.findElement(By.xpath("//ul[@class='pagination'] //a[contains(text(),'" + sNumero +"')]")).click();
    }

    public WebElement table() {
        WebElement wTable = driver.findElement(By.id("TableContents"));
        return wTable;
    }

    public List<WebElement> traerElementosColumna(int iColumna) {
        WebElement wTBody = table().findElement(By.tagName("tbody"));
        List<WebElement> wFilas = wTBody.findElements(By.tagName("tr"));
        List<WebElement> wDatos = new ArrayList<>();

        for (WebElement wAux : wFilas) {
            wDatos.add(wAux.findElements(By.tagName("td")).get(iColumna));
        }

        return wDatos;
    }

    public List<WebElement> traerElementosEncabezado() {
        List<WebElement> wEncabezado = table().findElement(By.tagName("thead")).findElements(By.tagName("th"));
        return wEncabezado;
    }

    public void ordenarTablaPor(String sNombreColumna) {
        List<WebElement> wEncabezado = traerElementosEncabezado();
        boolean bTablaOrdenada = false;
        for (WebElement wAux : wEncabezado) {
            if (wAux.getText().equals(sNombreColumna)) {
                wAux.click();
                bTablaOrdenada = true;
            }
        }
        if (!bTablaOrdenada) {
            System.out.println("La columna fue victima de Thanos o el nombre de la Columna es incorrecto!");
        }
    }

    public List<String> ordenarLista(List<WebElement> wListaWeb, String sTipoDeOrden) {
        List<String> sListaOrdenada = new ArrayList<>();

        for (WebElement wAux : wListaWeb) {
            sListaOrdenada.add(wAux.getText());
        }

        switch (sTipoDeOrden) {
            case "Ascendente":
                Collections.sort(sListaOrdenada);
                break;
            case "Descendente":
                Collections.sort(sListaOrdenada, Collections.reverseOrder());
                break;
            default:
                System.out.println("Tipo de Orden Incorrecto!");
        }


        return sListaOrdenada;
    }

    public void abrirAcciones(WebElement wElement) throws AWTException {
        //ACTION.moveToElement(wElement).perform();

        Point p = wElement.getLocation();
        int x = p.getX();
        int y = p.getY();
        Dimension d = wElement.getSize();
        int h = d.getHeight();
        int w = d.getWidth();
        Robot r = new Robot();
        r.mouseMove(x + (w/2), y+(h/2) + 60);
    }

    public void seleccionarBotonEnAcciones(WebElement wElement, String sBoton) throws AWTException {
        abrirAcciones(wElement);
        //ACTION.moveToElement(wElement.findElement(By.xpath("//i[contains(@class,'" + sBoton + "')] //.. //..")));

        Point p = wElement.findElement(By.xpath("//i[contains(@class,'" + sBoton + "')] //.. //..")).getLocation();
        int x = p.getX();
        int y = p.getY();
        Dimension d = wElement.findElement(By.xpath("//i[contains(@class,'" + sBoton + "')] //.. //..")).getSize();
        int h = d.getHeight();
        int w = d.getWidth();
        Robot r = new Robot();
        r.mouseMove(x + (w/2), y+(h/2) + 120);


        Robot ROBOT = new Robot();
        ROBOT.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        ROBOT.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }


    public void eliminar_filtro() throws AWTException {
        buscar("catata");
        List<WebElement> listaTablaFiltros = traerElementosColumna(5);
        seleccionarBotonEnAcciones(listaTablaFiltros.get(1), "trash");


    }



    public void buscar(String sText) {
        driver.findElement(By.xpath("//input[@type='search']")).sendKeys(sText);
        sleep(5000);
    }

    public void copiarFiltros() {
        driver.findElement(By.xpath("//a[@class='btn btn-default buttons-copy buttons-html5']")).click();
    }

    public void exportar(String sTipoArchivo) {
        driver.findElement(By.xpath("//a[@class='btn btn-default buttons-" + sTipoArchivo +" buttons-html5']")).click();
    }

    public void imprimir() {
        sleep(5000);
        driver.findElement(By.xpath("//a[@class='btn btn-default buttons-print']")).click();
    }

    public WebElement totalRegistros() {
        WebElement wTotalRegistros = driver.findElement(By.xpath("//div[@class='dataTables_info']"));
        return wTotalRegistros;
    }



}