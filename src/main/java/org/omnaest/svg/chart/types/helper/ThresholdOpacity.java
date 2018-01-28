package org.omnaest.svg.chart.types.helper;

public class ThresholdOpacity
{
    private String positiveColor      = "green";
    private String negativeColor      = "red";
    private double thresholdPrimary   = 0.5;
    private Double thresholdSecondary = null;

    public String determineBackgroundColor(double value)
    {
        return value < this.thresholdPrimary || (this.thresholdSecondary != null && value > this.thresholdSecondary) ? this.negativeColor : this.positiveColor;
    }

    public double determineBackgroundOpacity(double normValue)
    {
        double baseOpacity = 0.2;

        double value = this.determineDeltaValue(normValue, this.thresholdPrimary);

        if (this.thresholdSecondary != null)
        {
            if (normValue > this.thresholdSecondary)
            {
                value = this.determineDeltaValue(normValue, this.thresholdSecondary);
            }
            else if (normValue > this.thresholdPrimary && normValue < this.thresholdSecondary)
            {
                double value2 = this.determineDeltaValue(normValue, this.thresholdSecondary);
                value = Math.min(value, value2);
            }
        }

        double retval = baseOpacity + (1 - baseOpacity) * value;
        return retval;
    }

    private double determineDeltaValue(double normValue, double threshold)
    {
        return Math.abs(normValue - threshold) / threshold;
    }

    public void setPositiveColor(String positiveColor)
    {
        this.positiveColor = positiveColor;
    }

    public void setNegativeColor(String negativeColor)
    {
        this.negativeColor = negativeColor;
    }

    public void setThresholdPrimary(double thresholdPrimary)
    {
        this.thresholdPrimary = thresholdPrimary;
    }

    public void setThresholdSecondary(Double thresholdSecondary)
    {
        this.thresholdSecondary = thresholdSecondary;
    }

}
