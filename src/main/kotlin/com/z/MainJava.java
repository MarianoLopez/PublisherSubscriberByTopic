package com.z;


import static com.z.MainKt.NEW_TASK_TOPIC;
import static com.z.MainKt.NEW_USER_TOPIC;

public class MainJava {
    public static void main(String[] args){
        PublisherSubscriberService.INSTANCE.bind(new JavaSubscriberOne(),new JavaSubscriberTwo());
        RandomUserPublisher publisher1 = new RandomUserPublisher(NEW_USER_TOPIC);
        RandomTaskPublisher publisher2 = new RandomTaskPublisher(NEW_TASK_TOPIC);
    }
}

class JavaSubscriberOne{
    @SubscribeTo(topic = NEW_USER_TOPIC)
    public void onNewUser(User user) {
        System.out.println(("SubscriberOne.onNewUser: "+user.toString()));
    }

    @SubscribeTo(topic = NEW_TASK_TOPIC)
    public void onNewTask(Task task) {
        System.out.println("SubscriberOne.task: "+task.toString());
    }
}

class JavaSubscriberTwo{
    @SubscribeTo(topic = NEW_TASK_TOPIC)
    public void onNewTask(Task task) {
        System.out.println("SubscriberTwo.task: "+task.toString());
    }
}
