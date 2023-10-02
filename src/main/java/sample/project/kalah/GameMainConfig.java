package sample.project.kalah;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import sample.project.kalah.services.rules.interfaces.RuleChecker;

@Configuration
public class GameMainConfig
{
    @Autowired
    private RuleChecker firstMoveRuleChecker;

    @Autowired
    private RuleChecker nextPlayerMoveRuleChecker;

    @Autowired
    private RuleChecker samePlayerEmptyPitRuleChecker;

    @Autowired
    private RuleChecker samePlayerKalahRuleChecker;

    @Bean("rulesCheckers")
    public List<RuleChecker> getRulesCheckers()
    {
        return List.of(
                firstMoveRuleChecker,
                samePlayerKalahRuleChecker,
                samePlayerEmptyPitRuleChecker,
                nextPlayerMoveRuleChecker
        );
    }
}
