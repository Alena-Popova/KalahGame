package sample.project.kalah.generators;

import java.util.UUID;

import org.springframework.stereotype.Component;

import sample.project.kalah.generators.interfaces.DefaultGenerator;

@Component("idGenerator")
public class IdGeneratorImpl implements DefaultGenerator<UUID>
{
    @Override
    public UUID generate()
    {
        return new UUID(System.currentTimeMillis(), System.currentTimeMillis());
    }
}
