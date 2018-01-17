package jp.co.ack.crossword.application;

import java.util.ArrayList;
import java.util.Random;

import org.springframework.stereotype.Service;

import jp.co.ack.crossword.application.CreateService.Direction;
import jp.co.ack.crossword.interfaces.vo.template;

@Service
class RootingService {

	/**
	 * @see [概要] 開始位置取得
	 * @param temp
	 * @return
	 */
	public int getStartPoint(template temp){
		int res = 0;
		while(true){
			Random rnd = new Random();
			res = rnd.nextInt(temp.getTemplate().length);
			if(temp.getTemplate()[res].equals(temp.getNoempty())){
				break;
			}
		}
		return res;
	}

	/**
	 * @see [概要]  配置可能位置取得
	 * @param start    :配置開始位置
	 * @param flg      :縦方向：true 横方向：false
	 * @param nextList :配置可能方向リスト
	 * @param temp
	 */
	public void getRootList(int start,boolean flg, ArrayList<Direction> nextList,template temp){
		//配置可能な方向を検索
		int x = start % temp.getWidth();
		int y = start / temp.getWidth();
		int root = 1;
		if(flg){
			root = 0;
		}
		switch(root){
		case 0:
			if ((y-2) >= 0
			&& temp.getTemplate()[start-temp.getWidth()] == temp.getNoempty()
			&& temp.getTemplate()[start-(temp.getWidth()*2)] == temp.getNoempty()){
				nextList.add(Direction.Up);
			}
			if ((y+2) < temp.getHeight()
					&& temp.getTemplate()[start+temp.getWidth()] == temp.getNoempty()
					&& temp.getTemplate()[start+(temp.getWidth()*2)] == temp.getNoempty()){
				nextList.add(Direction.Down);
			}
			break;

		case 1:
			if ((x-2) >= 0
			&& temp.getTemplate()[start-1] == temp.getNoempty()
			&& temp.getTemplate()[start-2] == temp.getNoempty()){
				nextList.add(Direction.Left);
			}
			if ((x+2) < temp.getWidth()
					&& temp.getTemplate()[start+1] == temp.getNoempty()
					&& temp.getTemplate()[start+2] == temp.getNoempty()){
				nextList.add(Direction.Right);
			}
			break;
		}
	}


	/**
	 * @see [概要]  配置可能位置セット
	 * @param start    :配置開始位置
	 * @param nextList :配置可能方向リスト
	 * @param temp
	 * @return  配置可能位置リスト
	 */
	public int[] setRoot(int start,ArrayList<Direction> nextList,template temp){
		int next = 0;
		int maxSize = 0;

		int x = start % temp.getWidth();
		int y = start / temp.getWidth();

		Random rnd = new Random();
		int ran = rnd.nextInt(nextList.size());
		switch (nextList.get(ran)){
		case Up:	// 上
			next = temp.getWidth() * -1;
			maxSize = y;
			break;
		case Right:// 右
			next = 1;
			maxSize = temp.getWidth() - x;
			break;
		case Down:	// 下
			next = temp.getWidth();
			maxSize = temp.getHeight() - y;
			break;
		case Left:	// 左
			next = -1;
			maxSize = x;
			break;

		}

		int[] nextData = {next,maxSize};
		nextList.remove(ran);

		return nextData;
	}

	/**
	 * @see [概要]  配置開能リストからランダムに開始位置を取得
	 * @param flg  :縦方向：true 横方向：false
	 * @param temp
	 * @return ランダムに取得した開始位置
	 */
	public int getStart(boolean flg,template temp){
		if (temp.getPointList_Root(flg).size() == 0) return -1;
		// ランダムに開始座標を取得する
		Random rnd = new Random();
		int index = rnd.nextInt(temp.getPointList_Root(flg).size());
		int res = temp.getPointList_Root(flg).get(index);
		temp.deletePoint(res, flg);
		temp.deletePoint_Root(res, flg);
		return res;
	}

}