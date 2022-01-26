package games.triplet.thethief.script;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;

import games.rednblack.editor.renderer.components.physics.PhysicsBodyComponent;
import games.rednblack.editor.renderer.physics.PhysicsContact;
import games.rednblack.editor.renderer.scripts.BasicScript;
import games.rednblack.editor.renderer.utils.ComponentRetriever;

public class TestLockpick extends BasicScript implements PhysicsContact {

    private PhysicsBodyComponent mPhysicsBodyComponent;
    private final Vector2 impulse = new Vector2(0, 0);
    private final Vector2 speed = new Vector2(0, 0);
    public static final int JUMP = 0;
    private final PooledEngine mEngine;
    private PhysicsBodyComponent greenBarTransform;

    public TestLockpick(PooledEngine engine) {
        mEngine = engine;
    }

    @Override
    public void init(Entity item) {
        super.init(item);
        mPhysicsBodyComponent = ComponentRetriever.get(item, PhysicsBodyComponent.class);
        greenBarTransform = ComponentRetriever.get(item, PhysicsBodyComponent.class);
    }

    @Override
    public void act(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            movePlayer(JUMP);
            /*TransformComponent transformComponent = ComponentRetriever.get(entity, TransformComponent.class);
            if(transformComponent.y < 405f) {
                movePlayer (JUMP);
            }*/
        }
    }

    public void movePlayer(int direction) {
        Body body = mPhysicsBodyComponent.body;

        speed.set(body.getLinearVelocity());
        System.out.println("speed = " + speed);
        System.out.println("impulse = " + impulse);

        switch (direction) {
            case JUMP:
                impulse.set(speed.x, 40);
                break;
        }
        System.out.println("speed = " + speed);
        System.out.println("impulse = " + impulse);
        body.applyLinearImpulse(impulse.sub(speed), body.getWorldCenter(), true);
    }

    @Override
    public void dispose() {

    }

    @Override
    public void beginContact(Entity contactEntity, Fixture contactFixture, Fixture ownFixture, Contact contact) {
        System.out.println("Contact");
        mEngine.removeEntity(contactEntity);
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
