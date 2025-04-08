package TransfLinear;

import javax.imageio.ImageIO;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TransformacaoLinear {
    public static void main(String[] args) {
    	String arquivo = ".\\einstein.jpg";
    	String arquivoMod = ".\\einsteinMod.jpg";
        try {
            // Carregando a imagem em tons de cinza
            BufferedImage grayImage = ImageIO.read(new File(arquivo));

            // Aplicando transformações
            // BufferedImage transformedImage = AplicandoTransfLinear(grayImage, 1.0, 50.0);
            //BufferedImage transformedImage = AplicandoTransfLinearLog(grayImage, 25);
            BufferedImage transformedImage = AplicandoTransfLinearExp(grayImage, 0.0000000000000009);
             
            // Salvando a imagem transformada
            File output = new File(arquivoMod);
            ImageIO.write(transformedImage, "jpg", output);
            System.out.println("Imagem modificada com sucesso");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Função para aplicar a transformação linear: g = c * f + b
    private static BufferedImage AplicandoTransfLinear(BufferedImage image, double c, double b) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage transformedImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int grayValue = new Color(image.getRGB(x, y)).getRed();
                int transformedValue = (int) (c * grayValue + b);
                transformedValue = Math.min(255, Math.max(0, transformedValue));
                transformedImage.setRGB(x, y, new Color(transformedValue, transformedValue, transformedValue).getRGB());
            }
        }

        return transformedImage;
    }

    // Função para aplicar a transformação: g = c * log2(f + 1)
    private static BufferedImage AplicandoTransfLinearLog(BufferedImage image, double c) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage transformedImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int grayValue = new Color(image.getRGB(x, y)).getRed();
                int transformedValue = (int) (c * Math.log(grayValue + 1) / Math.log(2));
                transformedValue = Math.min(255, Math.max(0, transformedValue));
                transformedImage.setRGB(x, y, new Color(transformedValue, transformedValue, transformedValue).getRGB());
            }
        }

        return transformedImage;
    }

    // Função para aplicar a transformação: g = c * exp(f + 1)
    private static BufferedImage AplicandoTransfLinearExp(BufferedImage image, double c) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage transformedImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int grayValue = new Color(image.getRGB(x, y)).getRed();
                int transformedValue = (int) (c * Math.exp(grayValue + 1));
                transformedValue = Math.min(255, Math.max(0, transformedValue));
                transformedImage.setRGB(x, y, new Color(transformedValue, transformedValue, transformedValue).getRGB());
            }
        }

        return transformedImage;
    }
}

