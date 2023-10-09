package sample.project.kalah.services.rules;

import java.util.Optional;

import org.springframework.stereotype.Component;

import sample.project.kalah.dto.PlayerMoveData;
import sample.project.kalah.entity.sql.GameEntity;
import sample.project.kalah.entity.sql.PlayerMoveEntity;
import sample.project.kalah.services.rules.interfaces.RuleChecker;
import sample.project.kalah.utils.GameUtil;

@Component("nextPlayerMoveRuleChecker")
public class NextPlayerMoveRuleChecker implements RuleChecker
{
    @Override
    public boolean apply(final GameEntity game, final PlayerMoveData nextMove)
    {
        Optional<PlayerMoveEntity> lastMoveOPT = GameUtil.getLastMove(game.getMoves());
        if (lastMoveOPT.isEmpty())
        {
            return false;
        }
        PlayerMoveEntity lastMove = lastMoveOPT.get();
        boolean samePlayerMove = lastMove.getPlayer().equals(nextMove.getPlayer());
        return Boolean.FALSE.equals(samePlayerMove)
                && nextMove.isStartsOnPlayerSide();
    }
}
