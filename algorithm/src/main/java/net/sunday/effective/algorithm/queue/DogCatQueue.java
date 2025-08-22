package net.sunday.effective.algorithm.queue;

import java.util.ArrayDeque;
import java.util.Queue;

import static net.sunday.effective.algorithm.util.AssertUtils.assertFalse;
import static net.sunday.effective.algorithm.util.AssertUtils.assertTrue;

/**
 * 猫狗队列  宠物、狗和猫的类如下：
 * public class Pet { private String type;
 * public Pet(String type) { this.type = type; }
 * public String getPetType() { return this.type; }
 * }
 * public class Dog extends Pet { public Dog() { super("dog"); } }
 * public class Cat extends Pet { public Cat() { super("cat"); } }
 * 实现一种狗猫队列的结构，要求如下：
 * 用户可以调用add方法将cat类或dog类的实例放入队列中；
 * 用户可以调用pollAll方法，将队列中所有的实例按照进队列的先后顺序依次弹出；
 * 用户可以调用pollDog方法，将队列中dog类的实例按照进队列的先后顺序依次弹出；
 * 用户可以调用pollCat方法，将队列中cat类的实例按照进队列的先后顺序依次弹出；
 * 用户可以调用isEmpty方法，检查队列中是否还有dog或cat的实例；
 * 用户可以调用isDogEmpty方法，检查队列中是否有dog类的实例；
 * 用户可以调用isCatEmpty方法，检查队列中是否有cat类的实例。
 */
public class DogCatQueue {

    record PetCounter(int count, Pet pet) {
    }

    public static class Pet {
        private final String type;

        public Pet(String type) {
            this.type = type;
        }

        public String getPetType() {
            return this.type;
        }
    }

    public static class Dog extends Pet {
        public Dog() {
            super("dog");
        }
    }

    public static class Cat extends Pet {
        public Cat() {
            super("cat");
        }
    }


    private final Queue<PetCounter> dogQueue;
    private final Queue<PetCounter> catQueue;
    private int count;


    public DogCatQueue() {
        dogQueue = new ArrayDeque<>();
        catQueue = new ArrayDeque<>();
        count = 0;
    }


    public void add(Pet pet) {
        if (pet.getPetType().equals("dog")) {
            dogQueue.add(new PetCounter(count++, pet));
        } else if (pet.getPetType().equals("cat")) {
            catQueue.add(new PetCounter(count++, pet));
        }
    }

    public Pet pollAll() {
        if (!dogQueue.isEmpty() && !catQueue.isEmpty()) {
            return dogQueue.peek().count < catQueue.peek().count ? dogQueue.poll().pet : catQueue.poll().pet;
        } else if (!dogQueue.isEmpty()) {
            return dogQueue.poll().pet;
        } else if (!catQueue.isEmpty()) {
            return catQueue.poll().pet;
        }

        return null;
    }

    public Dog pollDog() {
        if (!dogQueue.isEmpty()) {
            return (Dog) dogQueue.poll().pet;
        }

        return null;
    }

    public Cat pollCat() {
        if (!catQueue.isEmpty()) {
            return (Cat) catQueue.poll().pet;
        }

        return null;
    }

    public boolean isEmpty() {
        return dogQueue.isEmpty() && catQueue.isEmpty();
    }

    public boolean isDogEmpty() {
        return dogQueue.isEmpty();
    }

    public boolean isCatEmpty() {
        return catQueue.isEmpty();
    }


    /**
     * for test
     */
    @SuppressWarnings("all")
    public static void main(String[] args) {
        DogCatQueue queue = new DogCatQueue();
        Pet dog1 = new Dog();
        Pet cat1 = new Cat();
        Pet dog2 = new Dog();
        Pet cat2 = new Cat();
        Pet dog3 = new Dog();
        Pet cat3 = new Cat();
        queue.add(dog1);
        queue.add(cat1);
        queue.add(dog2);
        queue.add(cat2);
        queue.add(dog3);
        queue.add(cat3);

        assertTrue(queue.pollAll().getPetType().equals("dog"));
        assertTrue(queue.pollDog().getPetType().equals("dog"));
        assertTrue(queue.pollCat().getPetType().equals("cat"));
        assertTrue(queue.pollAll().getPetType().equals("cat"));
        assertFalse(queue.isEmpty());
        assertFalse(queue.isDogEmpty());
        assertFalse(queue.isCatEmpty());

    }

}
