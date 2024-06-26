package com.gempukku.swccgo.db;

import com.gempukku.swccgo.cache.Cached;
import com.gempukku.swccgo.game.Player;
import org.apache.commons.collections4.map.LRUMap;

import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * A cached player database access object to help avoid unnecessary data access.
 */
public class CachedPlayerDAO implements PlayerDAO, Cached {
    private final PlayerDAO _delegate;
    private final Map<Integer, Player> _playerById = Collections.synchronizedMap(new LRUMap(500));
    private final Map<String, Player> _playerByName = Collections.synchronizedMap(new LRUMap(500));
    private final Map<String, List<String>> _similarAccountsByName = Collections.synchronizedMap(new LRUMap(500));

    /**
     * Creates a cached player database access object.
     * @param delegate the player database access object
     */
    public CachedPlayerDAO(PlayerDAO delegate) {
        _delegate = delegate;
    }

    @Override
    public void clearCache() {
        _playerById.clear();
        _playerByName.clear();
        _similarAccountsByName.clear();
    }

    @Override
    public int getItemCount() {
        return _playerById.size() + _playerByName.size() + _similarAccountsByName.size();
    }

    @Override
    public Player getPlayer(int id) {
        Player player = _playerById.get(id);
        if (player == null) {
            player = _delegate.getPlayer(id);
            if (player != null) {
                _playerById.put(id, player);
                _playerByName.put(player.getName(), player);
            }
        }
        return player;
    }

    @Override
    public Player getPlayer(String playerName) {
        return getPlayer(playerName, false);
    }

    @Override
    public Player getPlayer(String playerName, boolean includeDeactivated) {
        Player player = _playerByName.get(playerName);
        if (player == null) {
            player = _delegate.getPlayer(playerName, includeDeactivated);
            if (player != null) {
                _playerById.put(player.getId(), player);
                _playerByName.put(player.getName(), player);
            }
        }

        if(player != null && !includeDeactivated && player.hasType(Player.Type.DEACTIVATED))
            return null;

        return player;
    }

    @Override
    public boolean registerPlayer(String playerName, String password, String remoteAddr) throws SQLException, LoginInvalidException, RegisterNotAllowedException {
        boolean registered = _delegate.registerPlayer(playerName, password, remoteAddr);
        if (registered) {
            removePlayerFromCacheByName(playerName);
        }
        return registered;
    }

    @Override
    public Player loginPlayer(String playerName, String password) throws SQLException {
        Player player = _delegate.loginPlayer(playerName, password);
        if (player != null) {
            _playerById.put(player.getId(), player);
            _playerByName.put(player.getName(), player);
        }
        return player;
    }

    @Override
    public boolean resetUserPassword(String playerName) throws SQLException {
        final boolean success = _delegate.resetUserPassword(playerName);
        if (success) {
            removePlayerFromCacheByName(playerName);
        }
        return success;
    }

    @Override
    public boolean updateLastLoginIp(String playerName, String remoteAddr) throws SQLException {
        boolean success = _delegate.updateLastLoginIp(playerName, remoteAddr);
        if (success) {
            removePlayerFromCacheByName(playerName);
        }
        return success;
    }

    @Override
    public boolean updateLastReward(Player player, Integer previousReward, int currentReward) throws SQLException {
        boolean success = _delegate.updateLastReward(player, previousReward, currentReward);
        if (success) {
            removePlayerFromCacheByName(player.getName());
        }
        return success;
    }

    @Override
    public List<Player> findPlayersWithFlag(Player.Type flag) {
        return _delegate.findPlayersWithFlag(flag);
    }

    @Override
    public boolean setPlayerFlag(String playerName, Player.Type flag, boolean status) throws SQLException {
        final boolean success = _delegate.setPlayerFlag(playerName, flag, status);
        if (success) {
            removePlayerFromCacheByName(playerName);
        }
        return success;
    }

    @Override
    public boolean banPlayerPermanently(String playerName) throws SQLException {
        final boolean success = _delegate.banPlayerPermanently(playerName);
        if (success) {
            removePlayerFromCacheByName(playerName);
        }
        return success;
    }

    @Override
    public boolean banPlayerTemporarily(String playerName, long dateTo) throws SQLException {
        final boolean success = _delegate.banPlayerTemporarily(playerName, dateTo);
        if (success) {
            removePlayerFromCacheByName(playerName);
        }
        return success;
    }

    @Override
    public boolean unBanPlayer(String playerName) throws SQLException {
        final boolean success = _delegate.unBanPlayer(playerName);
        if (success) {
            removePlayerFromCacheByName(playerName);
        }
        return success;
    }

    @Override
    public List<Player> findSimilarAccounts(String playerName) {
        List<Player> similarAccounts = null;
        List<String> similarAccountNames = _similarAccountsByName.get(playerName);
        if (similarAccountNames == null) {
            similarAccountNames = new LinkedList<>();
            similarAccounts = _delegate.findSimilarAccounts(playerName);
            for (Player similarAccount : similarAccounts) {
                similarAccountNames.add(similarAccount.getName());
            }
            _similarAccountsByName.put(playerName, Collections.unmodifiableList(similarAccountNames));
        }
        if (similarAccounts == null) {
            similarAccounts = new LinkedList<>();
            for (String similarAccountName : similarAccountNames) {
                similarAccounts.add(getPlayer(similarAccountName, true));
            }
        }
        return Collections.unmodifiableList(similarAccounts);
    }

    /**
     * Removes a player from the cache by player name and clears the similar accounts cache.
     * @param playerName the player name
     */
    private void removePlayerFromCacheByName(String playerName) {
        Player player = _playerByName.get(playerName);
        if (player != null) {
            _playerById.get(player.getId());
            _playerByName.remove(playerName);
        }
        _similarAccountsByName.clear();
    }
}
