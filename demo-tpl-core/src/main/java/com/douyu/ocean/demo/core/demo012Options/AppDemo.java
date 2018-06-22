package com.douyu.ocean.demo.core.demo012Options;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Demo000
 * http://www.importnew.com/22060.html
 * @author weiqi
 * @create 2018-04-18 8:56:00
 */
public class AppDemo {

    /**
     * Optional 几种用法
     * public<U> Optional<U> map(Function<? super T, ? extends U> mapper)
     * public T orElse(T other)
     * public T orElseGet(Supplier<? extends T> other)
     * public void ifPresent(Consumer<? super T> consumer)
     * public Optional<T> filter(Predicate<? super T> predicate)
     * public<U> Optional<U> flatMap(Function<? super T, Optional<U>> mapper)
     * public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier)
     *
     */
    public static void test01() {
        //# SB用法 (不推荐作为 input参数或return参数, 应该仅仅用来简化if else
        UserEntity userEntity = new UserEntity();
        Optional<UserEntity> userEntityOpt = Optional.ofNullable(new UserEntity());
        Optional<UserEntity> userEntityOpt2 = Optional.of(new UserEntity()); // 明确知道不为空
        Optional<UserEntity> userEntityOpt3 = Optional.empty(); // 明确知道为空
        if (userEntityOpt.isPresent()) {
            System.out.println("userEntity: isPresent");
        } else {
            System.out.println("userEntity: not isPresent");
        }

        //# orElse, 存在即返回, 无则提供默认值
        UserEntity userEntity4 = userEntityOpt.orElse(null);
        System.out.println("userEntity4: "+userEntity4);

        //# ifPresent, 存在才对它做点什么
        userEntityOpt.ifPresent((entity) -> System.out.println("userEntity: ifPresent"));

        //# map
        String testMap = userEntityOpt.map(entity -> entity.toString()).orElse(null);
        List<Integer> itemScore = userEntityOpt
                .map(u -> u.getExamEntity())
                .map(examEntity -> examEntity.getScoreEntity())
                .map(scoreEntity -> scoreEntity.getItemScore()).orElse(Collections.emptyList());

        Optional<ExamEntity> examEntity = userEntityOpt.flatMap(userEntity1 -> Optional.ofNullable(userEntity1.getExamEntity()));
        System.out.println("testMap:"+testMap);
        System.out.println("itemScore:"+itemScore);

        //# filter
        // userEntityOpt.filter( userEntity1 -> userEntity.)
    }



    public static void main(String[] args) throws IOException {
        test01();
    }
}
