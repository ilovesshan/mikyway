package top.ilovesshan.mikyway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestController
public class BasicController {
    // 用于存储用户访问次数
    private static final Map<String, Integer> visitCountMap = new ConcurrentHashMap<>();

    // 随机名言警句
    private static final List<String> quotes = Arrays.asList(
            "不要温和地走进那个良夜。——狄兰·托马斯",
            "人生就像巧克力，你永远不知道下一颗是什么味道。——《阿甘正传》",
            "代码就像幽默，当你不得不解释的时候，它就已经不复存在了。——林纳斯·托瓦兹",
            "不要等待机会，而要创造机会。——乔治·伯纳德·肖",
            "今天也是充满希望的一天！",
            "编程不是为了计算机，而是为了人。——Donald Knuth",
            "好的代码就像好的笑话，你只需要看一遍就能懂。——Robert C. Martin",
            "程序员的快乐：代码运行成功。程序员的痛苦：代码运行失败。——匿名",
            "最好的代码是简洁的代码，而不是复杂的代码。——Jeff Atwood",
            "当你觉得代码写得还不错时，那就再优化一下吧。——Kimi",
            "计算机科学中的问题总可以用更多的层次来解决。——David Wheeler",
            "任何足够先进的技术都与魔法无法区分。——阿瑟·克拉克",
            "简单是终极的复杂。——史蒂夫·乔布斯",
            "在计算机科学中，我们站在巨人的肩膀上，但有时我们踩到了他们的脚。——Leslie Lamport",
            "计算机是人类思维的放大器。——Alan Kay",
            "软件就像熵，它总是趋向于混乱。——Leander Kahney",
            "优秀的代码是写给人看的，顺便让计算机执行。——Guido van Rossum",
            "调试是编程的另一半，而且它总是占据更多的时间。——Brian Kernighan",
            "软件是人类思想的结晶，它反映了人类的智慧和创造力。——Bjarne Stroustrup",
            "在编程中，最简单的事情往往是最难的。——Alan Perlis",
            "编程不仅仅是写代码，更是解决问题的艺术。——Grace Hopper",
            "复杂性是敌人，简单性是朋友。——Rich Hickey",
            "优秀的程序员知道何时该停止优化。——Donald Knuth"
    );

    @RequestMapping("/")
    public HashMap<String, Object> index(HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>();

        // 获取用户IP
        String ip = request.getRemoteAddr();


        // 更新访问次数
        visitCountMap.put(ip, visitCountMap.getOrDefault(ip, 0) + 1);
        int visitCount = visitCountMap.get(ip);

        long epochMilli = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        String epochMilliFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss").format(LocalDateTime.now());

        // 添加基本信息
        map.put("code", 200);
        map.put("message", "success");
        map.put("time", epochMilli);
        map.put("timeStr", epochMilliFormat);
        map.put("greeting", getGreeting());
        map.put("quote", quotes.get(new Random().nextInt(quotes.size())));
        map.put("visit_count", visitCount);
        map.put("joke", jokes.get(new Random().nextInt(jokes.size())));
        map.put("user_message", getUserBehaviorMessage(visitCount));
        map.put("fun_fact", getRandomFunFact());

        // 添加彩蛋信息（如果有）
        String easterEgg = getEasterEgg();
        if (easterEgg != null) {
            map.put("easter_egg", easterEgg);
        }
        log.info("用户{},在{}访问了系统，已访问{}次！", ip, epochMilliFormat, visitCount);
        return map;
    }

    // 动态问候语
    private String getGreeting() {
        int hour = LocalDateTime.now().getHour();
        if (hour >= 5 && hour < 12) {
            return "早上好！";
        } else if (hour >= 12 && hour < 18) {
            return "下午好！";
        } else {
            return "晚上好！";
        }
    }

    // 根据访问次数返回不同的消息
    private String getUserBehaviorMessage(int visitCount) {
        if (visitCount == 1) {
            return "欢迎第一次访问！希望你喜欢这里！";
        } else if (visitCount <= 5) {
            return "你已经来了 " + visitCount + " 次啦！看来这里还不错！";
        } else if (visitCount <= 10) {
            return "你已经来了 " + visitCount + " 次啦！是不是上瘾了？";
        } else {
            return "你已经来了 " + visitCount + " 次啦！你是我们的忠实用户！";
        }
    }

    // 随机笑话
    private static final List<String> jokes = Arrays.asList(
            "为什么Java开发者喜欢喝咖啡？因为Java是热的！",
            "程序员最喜欢的音乐是什么？——《代码之歌》！",
            "为什么程序员不喜欢在户外工作？因为那里有太多的 bugs！",
            "程序员的座右铭：今天不写代码，明天写更多！",
            "程序员的快乐：代码运行成功！程序员的痛苦：代码运行失败！"
    );


    // 获取彩蛋信息
    private String getEasterEgg() {
        LocalDateTime now = LocalDateTime.now();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();

        if (month == 4 && day == 1) {
            return "愚人节快乐！今天你被愚了没？(＾▽＾)";
        } else if (month == 2 && day == 14) {
            return "情人节快乐！愿代码也能甜蜜运行！(❤️)";
        } else if (month == 12 && day == 25) {
            return "圣诞节快乐！愿你的代码像圣诞树一样闪亮！(🎄)";
        } else if (month == 10 && day == 24) {
            return "程序员节快乐！今天少写点代码，多喝点咖啡！(☕)";
        } else if (month == 1 && day == 1) {
            return "新年快乐！新的一年，新的代码，新的希望！(🎉)";
        }
        return null;
    }

    // 获取随机趣味信息
    private String getRandomFunFact() {
        Random random = new Random();
        int funFactIndex = random.nextInt(3);

        switch (funFactIndex) {
            case 0:
                return "你知道吗？猫的叫声有超过100种，而狗的叫声只有大约10种！";
            case 1:
                return "随机数字：" + random.nextInt(100);
            case 2:
                return "随机颜色：" + getRandomColor();
            default:
                return "今天也是有趣的一天！";
        }
    }

    // 生成随机颜色
    private String getRandomColor() {
        Random random = new Random();
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        return String.format("#%02X%02X%02X", r, g, b);
    }
}
