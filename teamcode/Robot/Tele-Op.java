Skip to content
Search or jump to…

Pull requests
Issues
Marketplace
Explore
 
@CJD14147 
CJD14147
/
FTC-ultimateGoal
3
11
Code
Issues
4
Pull requests
1
Actions
Projects
1
Wiki
Security
Insights
Settings
FTC-ultimateGoal
/
teamcode
/
Robot
/
Tele-Op.java
in
main
 

Spaces

4

No wrap
1
package org.firstinspires.ftc.teamcode;
2
​
3
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
4
import com.qualcomm.robotcore.robot.Robot;
5
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
6
import com.qualcomm.robotcore.util.ElapsedTime;
7
​
8
import org.firstinspires.ftc.robotcore.external.navigation.Position;
9
​
10
import java.util.Locale;
11
//import android.graphics.Color;
12
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
13
import org.firstinspires.ftc.teamcode.Hardware;
14
​
15
import com.qualcomm.robotcore.hardware.DcMotor;
16
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
17
​
18
@TeleOp(name = "Teleop", group = "Tele")
19
​
20
public class Tele_Op extends LinearOpMode {
21
    Hardware robot = new Hardware();
22
    private ElapsedTime runtime = new ElapsedTime();
23
​
24
    @Override
25
    public void runOpMode() {
26
​
27
        robot.init(hardwareMap);
28
        sleep(500);
29
​
30
        int superPowerReduction = 1;
31
        int powerReduction = 1;
32
        double level = -1; /// was 0.66
33
        double twist = 0.47;
34
        double manual = 0;
35
​
36
        boolean isUp = false;
37
​
38
        boolean spinIsTrueForward = true;
39
        boolean spinIsTrueBackward = true;
40
        boolean clampIsTrue = true;
41
        boolean revIsTrue = true;
42
        boolean wristMovement = true;
43
        boolean elbowMovement = true;
44
        boolean intakeIsTrue = true;
45
        boolean elevatorIsTrue = true;
@CJD14147
Commit changes
Commit summary
Create Tele-Op.java
Optional extended description
Add an optional extended description…
 Commit directly to the main branch.
 Create a new branch for this commit and start a pull request. Learn more about pull requests.
 
© 2021 GitHub, Inc.
Terms
Privacy
Security
Status
Docs
Contact GitHub
Pricing
API
Training
Blog
About
