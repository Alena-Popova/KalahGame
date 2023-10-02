package sample.project.kalah.services.rules;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sample.project.kalah.dto.PlayerMoveRequest;
import sample.project.kalah.entity.sql.GameEntity;
import sample.project.kalah.entity.sql.PlayerMoveEntity;
import sample.project.kalah.services.rules.interfaces.RuleChecker;
import sample.project.kalah.utils.GameUtil;

@Component("nextPlayerMoveRuleChecker")
public class NextPlayerMoveRuleChecker implements RuleChecker
{
    private final GameUtil gameUtil;

    @Autowired
    public NextPlayerMoveRuleChecker(final GameUtil gameUtil)
    {
        this.gameUtil = gameUtil;
    }

    @Override
    public boolean apply(final GameEntity game, final PlayerMoveRequest nextMove)
    {
        Optional<PlayerMoveEntity> lastMoveOPT = gameUtil.getLastMove(game.getMoves());
        if (lastMoveOPT.isEmpty())
        {
            return false;
        }
        PlayerMoveEntity lastMove = lastMoveOPT.get();
        boolean samePlayerMove = lastMove.getPlayer().equals(nextMove.getPlayer());
        return Boolean.FALSE.equals(samePlayerMove)
                && nextMove.isPlayerSide()
                && Boolean.FALSE.equals(nextMove.isStartsInPlayerKalah());
    }
}
