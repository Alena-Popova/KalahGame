package sample.project.kalah.services.rules;

import java.util.Optional;

import org.springframework.stereotype.Component;

import sample.project.kalah.dto.PlayerMoveData;
import sample.project.kalah.entity.Player;
import sample.project.kalah.entity.sql.GameEntity;
import sample.project.kalah.entity.sql.PlayerMoveEntity;
import sample.project.kalah.services.rules.interfaces.RuleChecker;
import sample.project.kalah.utils.GameUtil;

@Component("firstMoveRuleChecker")
public class FirstMoveRuleChecker implements RuleChecker
{
    @Override
    public boolean apply(final GameEntity game, final PlayerMoveData nextMove)
    {
        Optional<PlayerMoveEntity> lastMoveOPT = GameUtil.getLastMove(game.getMoves());
        return lastMoveOPT.isEmpty()
                && Player.FIRST_PLAYER.equals(nextMove.getPlayer())
                && nextMove.isStartsOnPlayerSide();
    }
}
