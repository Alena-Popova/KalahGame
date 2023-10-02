package sample.project.kalah.services.rules;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sample.project.kalah.dto.PlayerMoveRequest;
import sample.project.kalah.entity.Player;
import sample.project.kalah.entity.sql.GameEntity;
import sample.project.kalah.entity.sql.PlayerMoveEntity;
import sample.project.kalah.services.rules.interfaces.RuleChecker;
import sample.project.kalah.utils.GameUtil;

@Component("firstMoveRuleChecker")
public class FirstMoveRuleChecker implements RuleChecker
{
    private final GameUtil gameUtil;

    @Autowired
    public FirstMoveRuleChecker(final GameUtil gameUtil)
    {
        this.gameUtil = gameUtil;
    }

    @Override
    public boolean apply(final GameEntity game, final PlayerMoveRequest nextMove)
    {
        Optional<PlayerMoveEntity> lastMoveOPT = gameUtil.getLastMove(game.getMoves());
        return lastMoveOPT.isEmpty()
                && Player.FIRST_PLAYER.equals(nextMove.getPlayer())
                && nextMove.isPlayerSide();
    }
}
