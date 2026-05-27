package com.gymbook.manage.util;

import java.util.*;

public class CollaborativeFilteringTool {

    // 用户-物品矩阵
    private Map<Integer, Set<Integer>> userItemMap;

    public CollaborativeFilteringTool() {
        userItemMap = new HashMap<>();
    }

    /**
     * 添加用户记录
     *
     * @param userId 用户ID
     * @param itemId 物品ID
     */
    public void addUserItem(int userId, int itemId) {
        userItemMap.computeIfAbsent(userId, k -> new HashSet<>()).add(itemId);
    }

    /**
     * 计算用户之间的余弦相似度
     *
     * @param user1Items 用户1的物品集合
     * @param user2Items 用户2的收品集合
     * @return 余弦相似度
     */
    private double cosineSimilarity(Set<Integer> user1Items, Set<Integer> user2Items) {
        Set<Integer> intersection = new HashSet<>(user1Items);
        intersection.retainAll(user2Items);

        double dotProduct = intersection.size();
        double norm1 = Math.sqrt(user1Items.size());
        double norm2 = Math.sqrt(user2Items.size());

        if (norm1 == 0 || norm2 == 0) {
            return 0;
        }
        return dotProduct / (norm1 * norm2);
    }

    /**
     * 获取与目标用户最相似的用户及其相似度
     *
     * @param targetUserId 目标用户ID
     * @param topN         返回的相似用户数量
     * @return 相似用户及其相似度的映射
     */
    public Map<Integer, Double> getSimilarUsersWithSimilarity(int targetUserId, int topN) {
        Map<Integer, Double> similarityMap = new HashMap<>();

        Set<Integer> targetUserItems = userItemMap.getOrDefault(targetUserId, new HashSet<>());

        for (Map.Entry<Integer, Set<Integer>> entry : userItemMap.entrySet()) {
            int userId = entry.getKey();
            if (userId == targetUserId) continue; // 跳过目标用户自己

            double similarity = cosineSimilarity(targetUserItems, entry.getValue());
            similarityMap.put(userId, similarity);
        }

        // 按相似度排序
        List<Map.Entry<Integer, Double>> sortedList = new ArrayList<>(similarityMap.entrySet());
        sortedList.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));

        // 返回前topN个相似用户及其相似度
        Map<Integer, Double> topSimilarUsers = new LinkedHashMap<>();
        for (int i = 0; i < Math.min(topN, sortedList.size()); i++) {
            topSimilarUsers.put(sortedList.get(i).getKey(), sortedList.get(i).getValue());
        }
        return topSimilarUsers;
    }

    /**
     * 为目标用户生成推荐物品及其推荐分数
     *
     * @param targetUserId 目标用户ID
     * @param topN         返回的推荐物品数量
     * @return 推荐的物品及其推荐分数的映射
     */
    public Map<Integer, Double> recommendItemsWithScores(int targetUserId, int topN) {
        Set<Integer> targetUserItems = userItemMap.getOrDefault(targetUserId, new HashSet<>());
        Map<Integer, Double> similarUsers = getSimilarUsersWithSimilarity(targetUserId, topN);

        // 统计相似用户预约的物品，并加权推荐分数
        Map<Integer, Double> itemScoreMap = new HashMap<>();
        for (Map.Entry<Integer, Double> entry : similarUsers.entrySet()) {
            int userId = entry.getKey();
            double similarity = entry.getValue();

            for (int itemId : userItemMap.get(userId)) {
                if (!targetUserItems.contains(itemId)) {
                    itemScoreMap.put(itemId, itemScoreMap.getOrDefault(itemId, 0.0) + similarity);
                }
            }
        }

        // 按物品的推荐分数排序
        List<Map.Entry<Integer, Double>> sortedItems = new ArrayList<>(itemScoreMap.entrySet());
        sortedItems.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));

        // 返回前topN个推荐物品及其推荐分数
        Map<Integer, Double> recommendedItems = new LinkedHashMap<>();
        for (int i = 0; i < Math.min(topN, sortedItems.size()); i++) {
            recommendedItems.put(sortedItems.get(i).getKey(), sortedItems.get(i).getValue());
        }
        return recommendedItems;
    }

    /**
     * 清空用户-物品预约数据
     */
    public void clearData() {
        userItemMap.clear();
    }

    /**
     * 获取当前用户-物品预约数据
     *
     * @return 用户-物品预约矩阵
     */
    public Map<Integer, Set<Integer>> getUserItemMap() {
        return userItemMap;
    }
}
