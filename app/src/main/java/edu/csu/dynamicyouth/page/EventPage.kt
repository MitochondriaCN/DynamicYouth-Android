package edu.csu.dynamicyouth.page

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import edu.csu.dynamicyouth.R
import edu.csu.dynamicyouth.component.EventCard

@Composable
fun EventPage(modifier: Modifier = Modifier) {
    //TODO: 使用ViewModel

    Column {
        EventCard(
            image = "https://54sh.csu.edu.cn/__local/D/23/05/FD4E90322198F14563B77730979_27F3676B_AFB8F.png",
            title = "“麓枫燃梦，百团湘汇”—中南大学2025年学生社团迎新活动圆满举行",
            description = "秋意渐浓，青春正燃。9月27日，中南大学2025年学生社团迎新活动在潇湘校区体育场副场拉开帷幕，全校114个学生社团齐聚招新；当晚，“百团之夜”迎新晚会精彩上演，吸引近万名学生到场参与、线上数万名观众同步互动，整场活动气氛热烈，赢得师生广泛好评。"
        ){
            //TODO: 一个炫酷的放大到详情界面动画
        }
    }
}