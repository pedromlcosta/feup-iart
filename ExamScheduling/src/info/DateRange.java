package info;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateRange {
	private Date start;
	private Date end;
	private long duration;

	public DateRange(Date start, Date end) {
		this.start = start;
		this.end = end;
		long diff = end.getTime() - start.getTime();
		this.duration = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

}
