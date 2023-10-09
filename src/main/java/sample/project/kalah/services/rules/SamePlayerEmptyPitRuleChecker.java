package sample.project.kalah.services.rules;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sample.project.kalah.dto.PlayerMoveData;
import sample.project.kalah.entity.Player;
import sample.project.kalah.entity.sql.GameEntity;
import sample.project.kalah.entity.sql.PlayerMoveEntity;
import sample.project.kalah.services.GameConfigurationService;
import sample.project.kalah.services.rules.interfaces.RuleChecker;
import sample.project.kalah.utils.GameUtil;

@Component("samePlayerEmptyPitRuleChecker")
public class SamePlayerEmptyPitRuleChecker implements RuleChecker
{
    private final GameConfigurationService gameConfigurationService;

    @Autowired
    public SamePlayerEmptyPitRuleChecker(final GameConfigurationService gameConfigurationService)
    {
        this.gameConfigurationService = gameConfigurationService;
    }

    @Override
    public boolean apply(final GameEntity game, final PlayerMoveData nextMove)
    {
        Optional<PlayerMoveEntity> lastMoveOPT = GameUtil.getLastMove(game.getMoves());
        if (lastMoveOPT.isEmpty())
        {
            return false;
        }
        PlayerMoveEntity lastMove = lastMoveOPT.get();

        List<Integer> stones = Player.FIRST_PLAYER.equals(lastMove.getPlayer())
                ? game.getFirstPlayerStonesList()
                : game.getSecondPlayerStonesList();

        boolean isNextMovePitOpposite = lastMove.getEndingPit() == (gameConfigurationService.getNumberOfHolesForEachParticipant() - nextMove.getStartingPit() - 1);
        boolean samePlayerMove = lastMove.getPlayer().equals(nextMove.getPlayer());
        return samePlayerMove
                && stones.get(lastMove.getEndingPit()) == 1
                && Boolean.FALSE.equals(nextMove.isStartsOnPlayerSide())
                && isNextMovePitOpposite;
    }
}
