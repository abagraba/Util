package util;

import java.util.Arrays;



public class MultiRange {

	private int[]	rangeData	= new int[8];
	private int		ranges;

	public void addRange(int start, int end) {
		if (ranges == 0 || end < rangeData[0]) {
			insertRange(0, start, end);
			return;
		}
		if (start > rangeData[ranges * 2 - 1]) {
			insertRange(ranges, start, end);
			return;
		}
		int s = -1, e = -1;
		for (int i = 0; i < ranges * 2; i++) {
			if (rangeData[i] < start)
				s = i;
			if (rangeData[i] < end)
				e = i;
		}
		boolean startOdd = s % 2 != 0;
		boolean endOdd = e % 2 != 0;

		if (s != -1 && startOdd && rangeData[s] + 1 == start)
			s--;
		if (e != -1 && !endOdd && rangeData[e] - 1 == end)
			e++;
		if (s == e) {
			if (startOdd)
				insertRange((s + 1) / 2, start, end);
			return;
		}
		int sRange = (s + 1) / 2 * 2;
		int eRange = e / 2 * 2 + 1;
		rangeData[sRange] = Math.min(rangeData[sRange], start);
		rangeData[sRange + 1] = Math.max(rangeData[eRange], end);
		deleteRanges(sRange / 2 + 1, eRange / 2);
	}

	private void insertRange(int index, int start, int end) {
		ensureSize(ranges * 2 + 2);
		for (int i = ranges * 2 - 1; i >= index * 2; i--)
			rangeData[i + 2] = rangeData[i];
		rangeData[index * 2] = start;
		rangeData[index * 2 + 1] = end;
		ranges++;
	}

	private void ensureSize(int i) {
		if (rangeData.length < i)
			rangeData = Arrays.copyOf(rangeData, ++i * 3 / 2);
	}

	private void deleteRanges(int startRange, int endRange) {
		if (startRange > endRange)
			return;
		for (int i = 0; i < (ranges - endRange - 1) * 2; i++)
			rangeData[startRange * 2 + i] = rangeData[endRange * 2 + 2 + i];
		ranges -= endRange - startRange + 1;
	}

	public int rangeStart(int i) {
		return rangeData[i * 2];
	}

	public int rangeEnd(int i) {
		return rangeData[i * 2 + 1];
	}

	public int ranges() {
		return ranges;
	}

	public int[] allRanges() {
		return Arrays.copyOf(rangeData, ranges * 2);
	}

}
