package sample.project.kalah;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;


@SpringBootApplication
@Import(GameMainConfig.class)
public class GameRunner
{
    public static void main(String[] args)
    {
        SpringApplication.run(GameRunner.class, args);
    }
}
