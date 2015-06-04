package com.ailink.framework.utils;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class CommonUtil {
	
	private CommonUtil(){
		
	}
	
	 /** 
     * 使用 Map按key进行排序 
     * @param map 
     * @return 
     */  
    public static Map<String, Object> sortMapByKeyObject(Map<String, Object> map) {  
        if (map == null || map.isEmpty()) {  
            return null;  
        }  
        Map<String, Object> sortMap = new TreeMap<String, Object>(
        		new MapKeyComparator());  
        sortMap.putAll(map);  
        return sortMap;  
    }
    
    /** 
     * 使用 Map按key进行排序 
     * @param resultMap 
     * @return 
     */  
    public static Map<String, Map<String, Integer>> sortMapByKeyMapInteger(Map<String, Map<String, Integer>> resultMap) {  
        if (resultMap == null || resultMap.isEmpty()) {  
            return null;  
        }  
        Map<String, Map<String, Integer>> sortMap = new TreeMap<String, Map<String, Integer>>(
        		new MapKeyComparator());  
        sortMap.putAll(resultMap);  
        return sortMap;  
    }
    /** 
     * 使用 Map按key进行排序 
     * @param resultMap 
     * @return 
     */  
    public static Map<String, Map<String, Double>> sortMapByKeyMapDouble(Map<String, Map<String, Double>> resultMap) {  
        if (resultMap == null || resultMap.isEmpty()) {  
            return null;  
        }  
        Map<String, Map<String, Double>> sortMap = new TreeMap<String, Map<String, Double>>(
        		new MapKeyComparator());  
        sortMap.putAll(resultMap);  
        return sortMap;  
    }
    
    /** 
     * 使用 Map按key进行排序 
     * @param map 
     * @return 
     */  
    public static Map<String, String> sortMapByKeyString(Map<String, String> map) {  
        if (map == null || map.isEmpty()) {  
            return null;  
        }  
        Map<String, String> sortMap = new TreeMap<String, String>(
        		new MapKeyComparator());  
        sortMap.putAll(map);  
        return sortMap;  
    } 
    
    /** 
     * 使用 Map按key进行排序 
     * @param map 
     * @return 
     */  
    public static Map<String, Integer> sortMapByKeyInteger(Map<String, Integer> map) {  
        if (map == null || map.isEmpty()) {  
            return null;  
        }  
        Map<String, Integer> sortMap = new TreeMap<String, Integer>(
        		new MapKeyComparator());  
        sortMap.putAll(map);  
        return sortMap;  
    } 
    
    /** 
     * 使用 Map按key进行排序 
     * @param map 
     * @return 
     */  
    public static Map<String, Double> sortMapByKeyDouble(Map<String, Double> map) {  
        if (map == null || map.isEmpty()) {  
            return null;  
        }  
        Map<String, Double> sortMap = new TreeMap<String, Double>(
        		new MapKeyComparator());  
        sortMap.putAll(map);  
        return sortMap;  
    }  
    
    /** 
     * 使用 Map按key进行排序 
     * @param map 
     * @return 
     */  
    public static Map<String, Long> sortMapByKeyLong(Map<String, Long> map) {  
        if (map == null || map.isEmpty()) {  
            return null;  
        }  
        Map<String, Long> sortMap = new TreeMap<String, Long>(
        		new MapKeyComparator());  
        sortMap.putAll(map);  
        return sortMap;  
    }  
}  
//比较器类  
 class MapKeyComparator implements Comparator<String>{  
    public int compare(String str1, String str2) {  
        return str1.compareTo(str2);  
    }  
}  
