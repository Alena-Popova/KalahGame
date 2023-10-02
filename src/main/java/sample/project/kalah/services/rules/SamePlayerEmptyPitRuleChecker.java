package sample.project.kalah.services.rules;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sample.project.kalah.dto.PlayerMoveRequest;
import sample.project.kalah.entity.Player;
import sample.project.kalah.entity.sql.GameEntity;
import sample.project.kalah.entity.sql.PlayerMoveEntity;
import sample.project.kalah.services.GameConfigurationService;
import sample.project.kalah.services.rules.interfaces.RuleChecker;
import sample.project.kalah.utils.GameUtil;

@Component("samePlayerEmptyPitRuleChecker")
public class SamePlayerEmptyPitRuleChecker implements RuleChecker
{
    private final GameUtil gameUtil;
    private final GameConfigurationService gameConfigurationService;

    @Autowired
    public SamePlayerEmptyPitRuleChecker(final GameUtil gameUtil, final GameConfigurationService gameConfigurationService)
    {
        this.gameUtil = gameUtil;
        this.gameConfigurationService = gameConfigurationService;
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

        Integer[] stones = Player.FIRST_PLAYER.equals(lastMove.getPlayer())
                ? game.getFirstPlayerStones()
                : game.getSecondPlayerStones();

        boolean isNextMovePitOpposite = lastMove.getEndingPit() == (gameConfigurationService.getNumberOfHolesForEachParticipant() - nextMove.getStartingPit() - 1);
        boolean samePlayerMove = lastMove.getPlayer().equals(nextMove.getPlayer());
        return samePlayerMove
                && stones[lastMove.getEndingPit()] == 1
                && Boolean.FALSE.equals(nextMove.isPlayerSide())
                && isNextMovePitOpposite;
    }
}
