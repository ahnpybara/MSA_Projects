package blog.infra;

import blog.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class NotifyHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<Notify>> {

    @Override
    public EntityModel<Notify> process(EntityModel<Notify> model) {
        return model;
    }
}
