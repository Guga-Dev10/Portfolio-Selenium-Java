package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PrimeiroTesteSelenium {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        // O Selenium WebDriver gerencia o ChromeDriver automaticamente nas versões modernas!
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void deveAbrirGoogleEValidarTitulo() {
        // Acessa o site do Google
        driver.get("https://www.google.com");

        // Valida se o título da página contém "Google"
        String titulo = driver.getTitle();
        assertTrue(titulo.contains("Google"), "O título da página deveria conter 'Google'");
    }

    @AfterEach
    public void tearDown() {
        // Fecha o navegador ao finalizar o teste para não deixar processos abertos
        if (driver != null) {
            driver.quit();
        }
    }
}