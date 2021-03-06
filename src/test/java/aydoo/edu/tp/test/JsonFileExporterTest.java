package aydoo.edu.tp.test;

import aydoo.edu.tp.entity.InputEntity;
import aydoo.edu.tp.entity.InputFieldEntity;
import aydoo.edu.tp.exporter.FileExporter;
import aydoo.edu.tp.exporter.JsonFileExporter;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonFileExporterTest {

    public static final String ENTITY_NAME = "alumno";
    public static final String FILE_NAME = "alumno.json";
    private File file;

    @Test
    public void generateFileJsonTest() throws Exception {
        List<InputFieldEntity> fields = new ArrayList<>();
        InputEntity entity = new InputEntity(ENTITY_NAME, fields);
        FileExporter jsonFileExporter = new JsonFileExporter(entity, FILE_NAME);
        jsonFileExporter.export();
        createFile();

        Assert.assertTrue(file.exists());
        file.deleteOnExit();
    }

    @Test
    public void fileMustContentNombreSebastianApellidoRoldanTest() throws Exception {
        List<InputFieldEntity> fields = new ArrayList<>();
        InputFieldEntity input1 = new InputFieldEntity("nombre", "sebastian");
        InputFieldEntity input2 = new InputFieldEntity("apellido", "roldan");
        fields.add(input1);
        fields.add(input2);
        InputEntity entity = new InputEntity(ENTITY_NAME, fields);
        FileExporter jsonFileExporter = new JsonFileExporter(entity, FILE_NAME);
        jsonFileExporter.export();
        createFile();

        String actual = getContentToFile();
        String expected = "{\"nombre\":\"sebastian\",\"apellido\":\"roldan\"}";

        Assert.assertEquals(expected, actual);
        file.deleteOnExit();
    }

    @Test(expected = IllegalArgumentException.class)
    public void createJsonFileExporterWithoutOutputFileNameShouldThrowException() {
        List<InputFieldEntity> fields = new ArrayList<>();
        InputFieldEntity input1 = new InputFieldEntity("nombre", "sebastian");
        InputFieldEntity input2 = new InputFieldEntity("apellido", "roldan");
        fields.add(input1);
        fields.add(input2);
        InputEntity entity = new InputEntity(ENTITY_NAME, fields);
        new JsonFileExporter(entity, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createJsonFileExporterWithoutInputEntityShouldThrowException() {
        new JsonFileExporter(null, "alumno.json");
    }

    @Test(expected = IllegalArgumentException.class)
    public void createJsonFileExporterWithEmptyOutputFileNameShouldThrowException() {
        List<InputFieldEntity> fields = new ArrayList<>();
        InputFieldEntity input1 = new InputFieldEntity("nombre", "sebastian");
        InputFieldEntity input2 = new InputFieldEntity("apellido", "roldan");
        fields.add(input1);
        fields.add(input2);
        InputEntity entity = new InputEntity(ENTITY_NAME, fields);
        new JsonFileExporter(entity, "");
    }

    private void createFile() {
        file = new File(FILE_NAME);
    }

    private String getContentToFile() throws IOException {
        FileReader fileReader = new FileReader(file);
        BufferedReader br = new BufferedReader(fileReader);
        String linea = br.readLine();
        br.close();
        return linea;
    }


}