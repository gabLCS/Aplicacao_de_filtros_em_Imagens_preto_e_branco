package Adjacencia;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class LetraA {
    public static void main(String[] args) {
        try {
            // Carregar a imagem
            BufferedImage originalImage = ImageIO.read(new File(".\\aviao.png"));
            if (originalImage == null) {
            System.out.println("A imagem não pôde ser carregada.");
            return;
            }
            int width = originalImage.getWidth();
            int height = originalImage.getHeight();
            // Criar uma nova imagem para armazenar os pixels de fronteira
            BufferedImage borderImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
            for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
            borderImage.setRGB(x, y, 0xFFFFFF);
            }
            }
            // Definir um limite para identificar os pixels de fronteira
            int threshold = 50; // Ajuste o valor conforme necessário
            for (int x = 1; x < width - 1; x++) {
                for (int y = 1; y < height - 1; y++) {
                int centerPixel = originalImage.getRGB(x, y) & 0xFF;
                int[] surroundingPixels = new int[8];
                surroundingPixels[0] = originalImage.getRGB(x - 1, y - 1) & 0xFF;
                surroundingPixels[1] = originalImage.getRGB(x, y - 1) & 0xFF;
                surroundingPixels[2] = originalImage.getRGB(x + 1, y - 1) & 0xFF;
                surroundingPixels[3] = originalImage.getRGB(x - 1, y) & 0xFF;
                surroundingPixels[4] = originalImage.getRGB(x + 1, y) & 0xFF;
                surroundingPixels[5] = originalImage.getRGB(x - 1, y + 1) & 0xFF;
                surroundingPixels[6] = originalImage.getRGB(x, y + 1) & 0xFF;
                surroundingPixels[7] = originalImage.getRGB(x + 1, y + 1) & 0xFF;
                boolean isBorder = false;
                // Verificando se o pixel possui algum vizinho que pertença ao fundo
                    for (int pixel : surroundingPixels) {
                        if (Math.abs(centerPixel - pixel) > threshold) {
                            isBorder = true;
                            break;
                        }
                    }
                // Inserindo o pixel na imagem nova
                    if (isBorder) {
                        borderImage.setRGB(x, y, centerPixel);
                    }
                }
            }
            // Salvar a imagem de fronteira
            File output = new File(".\\aviaoAdjacencia_8.png");
            ImageIO.write(borderImage, "png", output);

            // Exibir a imagem de fronteira (opcional)
            // JFrame frame = new JFrame("Imagem de Fronteira");
            // frame.add(new JLabel(new ImageIcon(borderImage)));
            // frame.pack();
            // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            // frame.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}