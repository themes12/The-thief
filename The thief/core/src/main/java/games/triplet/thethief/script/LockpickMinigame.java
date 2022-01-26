package games.triplet.thethief.script;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;

import games.rednblack.editor.renderer.components.DimensionsComponent;
import games.rednblack.editor.renderer.components.TransformComponent;
import games.rednblack.editor.renderer.physics.PhysicsContact;
import games.rednblack.editor.renderer.scripts.BasicScript;
import games.rednblack.editor.renderer.utils.ComponentRetriever;

import java.util.Random;


public class LockpickMinigame extends BasicScript {

    private Entity key;
    private Entity bar;
    private final Entity box;
    private TransformComponent transformComponent;
    private TransformComponent boxComponent;
    private TransformComponent contractorComponent;
    private DimensionsComponent boxDComponent;
    private DimensionsComponent contractorDComponent;
    private TransformComponent barComponent;
    private float timediff = 0;
    private Random random = new Random();
    private final float MAX_TIME = 0.5f + random.nextFloat() * (1f - 0.5f);
    private boolean status = true;
    private boolean isPause = false;
    private float timeContractor = 0;
    private float MAX_HOLD_TIME = 0.5f;
    private float timeDelayProgressBar = 0;
    private float MAX_PROGRESS_HOLD_TIME = 0.5f;
    static public boolean isSuccess = false;

    public LockpickMinigame(Entity box, Entity key, Entity bar){
        this.box = box;
        this.key = key;
        this.bar = bar;
    }

    @Override
    public void init(Entity item) {
        super.init(item);
        contractorComponent = ComponentRetriever.get(item, TransformComponent.class);
        contractorDComponent = ComponentRetriever.get(item, DimensionsComponent.class);
        transformComponent = ComponentRetriever.get(key, TransformComponent.class);
        boxDComponent = ComponentRetriever.get(box, DimensionsComponent.class);
        boxComponent = ComponentRetriever.get(box, TransformComponent.class);
        barComponent = ComponentRetriever.get(bar, TransformComponent.class);
    }

    @Override
    public void act(float delta) {
        isKeyAndBarContact(delta);
        checkProgressBarPosition();
        if(timediff > MAX_TIME){
            isPause = !isPause;
            timediff = 0;
        }else{
            checkKeyPosition();
            if(!isPause){
                if(status){
                    transformComponent.y += (150*delta);
                }else{
                    transformComponent.y -= (300*delta);
                }
            }
        }

        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            timeContractor = 0;
            contractorComponent.y += (150*delta);
        }

        if(timeContractor > MAX_HOLD_TIME){
            contractorComponent.y -= (150*delta);
        }

        if(checkContractorPosition() == 0) {
            contractorComponent.y = (boxDComponent.height + boxComponent.y - contractorDComponent.height) - 15f;
        }else if(checkContractorPosition() == 1){
            contractorComponent.y = (boxComponent.y + 20f);
        }
        timeContractor += delta;
        timediff += delta;

    }

    private void checkProgressBarPosition(){
        if(barComponent.scaleY > 196f){
            barComponent.scaleY = 196f;
            isSuccess = true;
        }

        if(barComponent.scaleY < 1f){
            barComponent.scaleY = 1f;
        }
    }

    private void isKeyAndBarContact(float delta){
        if(((transformComponent.y - 30f) < (contractorComponent.y + (contractorDComponent.height / 2))) && ((transformComponent.y + 30f) > (contractorComponent.y + (contractorDComponent.height / 2)))){
            barComponent.scaleY += 0.8f;
            timeDelayProgressBar = 0;
        }else{
            if(timeDelayProgressBar > MAX_PROGRESS_HOLD_TIME){
                barComponent.scaleY -= 0.4;
            }
            timeDelayProgressBar += delta;
        }
    }

    private int checkContractorPosition() {
        int status = -1;
        if((contractorComponent.y > ((boxDComponent.height + boxComponent.y - contractorDComponent.height) - 15f))) {
            status = 0;
        }

        if(contractorComponent.y < (boxComponent.y + 20f)){
            status = 1;
        }
        return status;
    }

    private void checkKeyPosition() {
        if((transformComponent.y > ((boxDComponent.height + boxComponent.y) - 35f))) {
            //System.out.println("greater than");
            status = false;
        }

        if(transformComponent.y < (boxComponent.y + 25f)){
            //System.out.println("less than");
            status = true;
        }
    }

    @Override
    public void dispose() {

    }

}
