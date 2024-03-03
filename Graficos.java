import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Graficos extends JPanel {
    private GrafoMatriz grafoMatriz;
    private Nodo[][] grid;

    public Graficos(GrafoMatriz grafoMatriz) {
        this.grafoMatriz = grafoMatriz;
        createGrid();
    }

    public void updateGUI() {
        repaint();
    }

    private void createGrid() {
        int rows = grafoMatriz.getFilas();
        int cols = grafoMatriz.getColumnas();

        grid = new Nodo[rows][cols];
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                grid[x][y] = grafoMatriz.getNodo(x, y);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw((Graphics2D) g);
    }

    private void draw(Graphics2D g) {
        int rows = grafoMatriz.getFilas();
        int cols = grafoMatriz.getColumnas();

        int tileSizeX = getWidth() / cols;
        int tileSizeY = getHeight() / rows;

        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                Nodo current = grid[x][y];
                int rx = x * tileSizeX;
                int ry = y * tileSizeY;

                if (current.getBloqueado()) {
                    g.setColor(Color.BLACK);
                } else {
                    switch (current.getEstado().toString()) {
                        case "ABIERTO":
                            g.setColor(Color.BLUE);
                            break;
                        case "CERRADO":
                            g.setColor(Color.RED);
                            break;
                        case "META":
                            g.setColor(Color.GREEN);
                            break;
                        case "INICIO":
                            g.setColor(Color.YELLOW);
                            break;
                        default:
                            g.setColor(Color.WHITE);
                            break;
                    }
                }

                g.fillRect(rx, ry, tileSizeX, tileSizeY);
                g.setColor(Color.BLACK);
                g.drawRect(rx, ry, tileSizeX, tileSizeY);

                g.setColor(Color.BLACK);
                g.drawString("(" + x + "," + y + ")", rx + 5, ry + 15);
            }
        }
    }
}