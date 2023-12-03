<h1 style="text-align: center; color: #06ce7e;">Documentation for "Task Manager App"</h1>
    <table style="width:100%; border: 1px solid red; border-collapse: collapse;">
        <tr>
            <td style="border: 1px solid red;padding:4px;">Primary Language: </td>
            <td style="border: 1px solid red;padding:4px;">Kotlin, XML</td>
        </tr>
        <tr>
            <td style="border: 1px solid red;padding:4px;">Minimum SDK</td>
            <td style="border: 1px solid red;padding:4px;">21</td>
        </tr>
        <tr>
            <td style="border: 1px solid red;padding:4px;">Target SDK</td>
            <td style="border: 1px solid red;padding:4px;">34</td>
        </tr>
        <tr>
            <td style="border: 1px solid red;padding:4px;">Architecture Followed</td>
            <td style="border: 1px solid red;padding:4px;">MVVM</td>
        </tr>
               <tr>
            <td style="border: 1px solid red;padding:4px;">Principle tried to follow: </td>
            <td style="border: 1px solid red;padding:4px;">SOLID</td>
        </tr>
    </table>
<hr>
<p>
    I make this project as an assignment given to me by ITmedicus solutions to proof my ability.
</p>
<p>
    To make this project I followed MVVM architecture which one is very easy to manage and hassle free for code reusability rather than other architecture like MVP or MVC. 
    I have tried my best to follow clean code style and making code reusuable. 
    <br>
   <b> In building this project</b>, I have faced two types of challenges. They are described below: <br> 
    The first challenge was time picking after date picker. Firstly I thought how can I combine both and use in one variable. Then I read on Date() object which one is already familiar for me.
    Then I used the Date() object that can be generated from calender instance. And I set my variable type as Date. 
    <pre>
        selectDateTime = Calendar.getInstance()
        // and the below code returns Date() object
        selectDateTime.time
    </pre>
    <br>
    Second challenge was setting alarm. But google documentation and youtube video helps me to solve the problem. After reading and watching I used the BroadcastReceiver() for alarm handling after trigger and set Notification to notify user about their due tasks. If user click on notification they will redirect to MainActivity where DisplayFragment is set to home on nav graph. I mean user find the tasks list.<br>
    For triggering alarm, I used AlarmManager on my AlarmSchedular class. 
     <br>
</p>
<p>
    <strong>Working: </strong> App has two screens, one for displaying list of tasks. and another one is for adding and updating task. I used fragment. Because this app should be single activity project.
    App trigger an alarm when due time has come and user haven't completed his task. Here Notification is set for alarming the user for his pending task. 
    <br>
    <b>Note: </b>This app will not trigger alarm in due date when user already completed his task. And For deleting "You have to swap the list view item".
</p>
