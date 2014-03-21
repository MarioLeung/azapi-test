/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.dcr.az.vis.player.impl.entities;

import bgu.dcr.az.vis.player.api.Layer;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 *
 * @author Zovadi
 */
public class SpriteBasedEntity extends CanvasLayeredEntity {

    private final Image image;

    public SpriteBasedEntity(long entityId, Class<? extends Layer> layerClazz, String filepath) throws FileNotFoundException {
        this(entityId, layerClazz, new FileInputStream(filepath));
    }

    public SpriteBasedEntity(long entityId, Class<? extends Layer> layerClazz, InputStream in) {
        this(entityId, layerClazz, new Image(in));
    }

    public SpriteBasedEntity(long entityId, Class<? extends Layer> layerClazz, Image image) {
        super(entityId, layerClazz);

        this.image = image;
    }

    @Override
    protected final void _draw(GraphicsContext gc) {
        gc.translate(-image.getWidth() / 2.0, -image.getHeight() / 2.0);
        gc.drawImage(image, 0, 0, image.getWidth(), image.getHeight());
    }

}