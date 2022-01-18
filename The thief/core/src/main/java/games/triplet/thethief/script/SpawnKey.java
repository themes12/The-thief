package games.triplet.thethief.script;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;

import games.rednblack.editor.renderer.components.ActionComponent;
import games.rednblack.editor.renderer.components.BoundingBoxComponent;
import games.rednblack.editor.renderer.components.CompositeTransformComponent;
import games.rednblack.editor.renderer.components.DimensionsComponent;
import games.rednblack.editor.renderer.components.NodeComponent;
import games.rednblack.editor.renderer.components.PolygonComponent;
import games.rednblack.editor.renderer.components.TransformComponent;
import games.rednblack.editor.renderer.components.ViewPortComponent;
import games.rednblack.editor.renderer.components.physics.PhysicsBodyComponent;
import games.rednblack.editor.renderer.physics.PhysicsContact;
import games.rednblack.editor.renderer.scripts.BasicScript;
import games.rednblack.editor.renderer.utils.ComponentRetriever;

import java.util.Random;


public class SpawnKey extends BasicScript implements PhysicsContact {

    private Entity key;
    private TransformComponent transformComponent;
    private DimensionsComponent boxDComponent;
    private TransformComponent boxComponent;
    private Random random;
    private float keyPosition;
    private final Entity box;
    private final Entity greenBox;

    public SpawnKey(Entity box, Entity greenBox){
        this.box = box;
        this.greenBox = greenBox;
    }

    @Override
    public void init(Entity item) {
        super.init(item);
        key = item;
        random = new Random();
        transformComponent = ComponentRetriever.get(item, TransformComponent.class);
        boxDComponent = ComponentRetriever.get(box, DimensionsComponent.class);
        boxComponent = ComponentRetriever.get(box, TransformComponent.class);
        System.out.println(boxComponent.y);
        System.out.println(boxDComponent.height + boxComponent.y);
    }

    @Override
    public void act(float delta) {
        keyPosition = transformComponent.y;
        while((keyPosition < ((boxDComponent.height + boxComponent.y)-50)) && (keyPosition > boxComponent.y)){
            keyPosition = (random.nextInt(900)-500) + transformComponent.y;
            transformComponent.y += keyPosition * delta;
        }
        /*while(true){
            System.out.println("Enter Loop");
            keyPosition = (random.nextInt(900)-500) + transformComponent.y;
            System.out.println(keyPosition);

            if((keyPosition < (boxDComponent.height + boxComponent.y)) && (keyPosition > boxComponent.y)){
                break;
            }
        }*/
        System.out.println(transformComponent.y);
    }

    @Override
    public void dispose() {

    }

    @Override
    public void beginContact(Entity contactEntity, Fixture contactFixture, Fixture ownFixture, Contact contact) {

    }

    @Override
    public void endContact(Entity contactEntity, Fixture contactFixture, Fixture ownFixture, Contact contact) {

    }

    @Override
    public void preSolve(Entity contactEntity, Fixture contactFixture, Fixture ownFixture, Contact contact) {

    }

    @Override
    public void postSolve(Entity contactEntity, Fixture contactFixture, Fixture ownFixture, Contact contact) {

    }
}
