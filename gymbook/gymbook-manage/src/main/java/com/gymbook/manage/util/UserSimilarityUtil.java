package com.gymbook.manage.util;

import com.gymbook.manage.DTO.UserCourseDTO;
import com.gymbook.manage.DTO.UserSimilarityDTO;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 基于用户的协同过滤推荐算法
 */
public class UserSimilarityUtil {

    /**
     * 更新用户的课程预约行为，最终插入数据库中
     * @param userId 用户id
     * @param courseId 健身课程id
     * @return true则更新成功，false则更新失败
     */
    public static boolean updateBookingBehavior(Long userId, Long courseId) {
        boolean flag = false;
        return flag;
    }

    /**
     * 将用户的健身课程预约行为组装成一个map,key为userId，value也是一个map，这个map记录的是二级类目以及它对应的预约量
     * @param userCourseList 用户的健身课程预约行为列表
     * @return 组装好的用户的健身课程预约行为的map集合
     */
    public static ConcurrentHashMap<Long, ConcurrentHashMap<Long, Long>> assembleUserBehavior(List<UserCourseDTO> userCourseList) {
        ConcurrentHashMap<Long, ConcurrentHashMap<Long, Long>> activeMap = new ConcurrentHashMap<>();

        // 遍历查询到的用户预约课程数据
        for (UserCourseDTO userCourse : userCourseList) {
            Long userId = userCourse.getUserId();       // 用户ID
            Long courseId = userCourse.getCourseId();   // 课程ID
            Long count = userCourse.getCount(); // 预约次数

            // 判断 activeMap 中是否已经存在该 userId 的信息
            if (activeMap.containsKey(userId)) {
                ConcurrentHashMap<Long, Long> courseMap = activeMap.get(userId);
                courseMap.put(courseId, count);
                activeMap.put(userId, courseMap);
            } else {
                // 不存在则新建
                ConcurrentHashMap<Long, Long> courseMap = new ConcurrentHashMap<>();
                courseMap.put(courseId, count);
                activeMap.put(userId, courseMap);
            }
        }

        return activeMap;
    }

    /**
     * 计算用户与用户之间的相似性，返回计算出的用户与用户之间的相似度对象
     * @param activeMap 用户对健身课程的预约行为的一个map集合
     * @return 计算出的用户与用户之间的相似度的对象存储形式
     */
    public static List<UserSimilarityDTO> calcSimilarityBetweenUsers(Map<Long, Map<Long, Long>> activeMap) {
        List<UserSimilarityDTO> similarityList = new ArrayList<>();

        // 获取所有用户ID
        List<Long> userIdList = new ArrayList<>(activeMap.keySet());

        // 小于两个用户，直接返回
        if (userIdList.size() < 2) {
            return similarityList;
        }

        // 遍历所有用户对，计算相似度
        for (int i = 0; i < userIdList.size() - 1; i++) {
            for (int j = i + 1; j < userIdList.size(); j++) {
                Long userA = userIdList.get(i);
                Long userB = userIdList.get(j);

                Map<Long, Long> userACourses = activeMap.get(userA);
                Map<Long, Long> userBCourses = activeMap.get(userB);

                // 计算余弦相似度
                double similarity = cosineSimilarity(userACourses, userBCourses);

                // 封装用户相似度数据
                UserSimilarityDTO similarityDTO = new UserSimilarityDTO();
                similarityDTO.setUserId(userA);
                similarityDTO.setUserRefId(userB);
                similarityDTO.setSimilarity(similarity);

                similarityList.add(similarityDTO);
            }
        }
        return similarityList;
    }

    /**
     * 计算余弦相似度
     * @param userACourses
     * @param userBCourses
     * @return
     */
    private static double cosineSimilarity(Map<Long, Long> userACourses, Map<Long, Long> userBCourses) {
        double numerator = 0.0; // 分子
        double sumA = 0.0; // 用户A的模长
        double sumB = 0.0; // 用户B的模长

        // 遍历A用户的课程
        for (Map.Entry<Long, Long> entry : userACourses.entrySet()) {
            Long courseId = entry.getKey();
            Long enrollCountA = entry.getValue();

            // 如果B用户也预约了这个课程，计算向量点积
            if (userBCourses.containsKey(courseId)) {
                numerator += enrollCountA * userBCourses.get(courseId);
            }
            // 计算A的向量长度
            sumA += Math.pow(enrollCountA, 2);
        }

        // 计算B的向量长度
        for (Long enrollCountB : userBCourses.values()) {
            sumB += Math.pow(enrollCountB, 2);
        }

        double denominator = Math.sqrt(sumA) * Math.sqrt(sumB);

        // 避免分母为 0
        return denominator == 0 ? 0 : numerator / denominator;
    }


    /**
     * 找出与userId预约行为最相似的topN个用户
     * @param userId 需要参考的用户id
     * @param userSimilarityDTOList 用户相似度列表
     * @param topN 需要的相似用户数量
     * @return 最相似的topN个用户ID列表
     */
    public static List<Long> getSimilarityBetweenUsers(Long userId, List<UserSimilarityDTO> userSimilarityDTOList, Integer topN) {
        List<Long> similarityList = new ArrayList<>(topN);

        // 小根堆（最小优先队列），保存前N个相似用户
        PriorityQueue<UserSimilarityDTO> minHeap = new PriorityQueue<>(Comparator.comparingDouble(UserSimilarityDTO::getSimilarity));

        for (UserSimilarityDTO similarityDTO : userSimilarityDTOList) {
            if (similarityDTO.getUserId().equals(userId) || similarityDTO.getUserRefId().equals(userId)) {
                minHeap.offer(similarityDTO);
                if (minHeap.size() > topN) {
                    minHeap.poll();  // 保持堆大小为topN
                }
            }
        }

        // 取出相似用户ID（不要取当前用户自己）
        while (!minHeap.isEmpty()) {
            UserSimilarityDTO similarityDTO = minHeap.poll();
            similarityList.add(similarityDTO.getUserId().equals(userId) ? similarityDTO.getUserRefId() : similarityDTO.getUserId());
        }

        return similarityList;
    }

    /**
     * 获取推荐课程
     * @param userId 当前用户
     * @param similarUsers 最相似的用户ID列表
     * @param userCourseList 所有用户的课程预约记录
     * @return 推荐课程ID列表
     */
    public static List<Long> getRecommendCourses(Long userId, List<Long> similarUsers, List<UserCourseDTO> userCourseList) {
        List<Long> recommendCourses = new ArrayList<>();

        List<Long> recommeddateProductList = new ArrayList<Long>();

        // userId的预约课程行为列表
        List<UserCourseDTO> userIdCourseList = findUsersBrowsBehavior(userId, userCourseList);

        // 排序
        Collections.sort(userIdCourseList, new Comparator<UserCourseDTO>(){
            @Override
            public int compare(UserCourseDTO o1, UserCourseDTO o2) {
                return o1.getCourseId().compareTo(o2.getCourseId());
            }
        });

        // 从与useId预约行为相似的每个用户中找出一个推荐的健身课程
        for (Long refId : similarUsers) {
            // 计算当前用户所预约的健身课程次数与被推荐的用户所预约的健身课程的次数的差值
            // 找到当前这个用户的预约健身课程行为
            List<UserCourseDTO> currCourseList = findUsersBrowsBehavior(refId, userCourseList);

            // 排序
            Collections.sort(currCourseList, new Comparator<UserCourseDTO>(){
                @Override
                public int compare(UserCourseDTO o1, UserCourseDTO o2) {
                    return o1.getCourseId().compareTo(o2.getCourseId());
                }
            });

            // 记录差值最大的二级类目的id
            long maxCategory2 = 0L;

            // 记录最大的差值
            double maxDifference = 0.0;
            for (int i = 0; i < currCourseList.size(); i++) {
                // 求出预约量差值最大的健身课程，即为要推荐的课程
                double difference = Math.abs(currCourseList.get(i).getCount() - userIdCourseList.get(i).getCount());
                if (difference > maxDifference) {
                    maxDifference = difference;
                    maxCategory2 = currCourseList.get(i).getCourseId();
                }
            }
            recommeddateProductList.add(maxCategory2);
        }
        return recommeddateProductList;
    }


    /**
     * 找到当前用户的浏览行为列表
     * @param userId 当前用户id
     * @param userCourseList 所有用户的浏览行为列表
     * @return 当前用户的浏览行为列表
     */
    public static List<UserCourseDTO> findUsersBrowsBehavior(Long userId, List<UserCourseDTO> userCourseList) {
        List<UserCourseDTO> currCourseList = new ArrayList<UserCourseDTO>();
        for (UserCourseDTO userCourseDTO : userCourseList) {
            if (userCourseDTO.getUserId().equals(userId)) {
                currCourseList.add(userCourseDTO);
            }
        }
        return currCourseList;
    }
}
